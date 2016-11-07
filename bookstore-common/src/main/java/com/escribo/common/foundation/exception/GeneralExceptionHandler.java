package com.escribo.common.foundation.exception;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;

import com.escribo.common.util.MsgFactory;




@Configurable
public class GeneralExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	@Value("${general.debug_mode}")
	private Boolean isDebugMode;

	private static Log log = LogFactory.getLog(GeneralExceptionHandler.class);

	public GeneralExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
		
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
			boolean removeFromPile = true;
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			Throwable t = context.getException();
			Throwable rootCause = t;
			while (rootCause.getCause() != null) {
				rootCause = rootCause.getCause();
			}
			if (rootCause instanceof ViewExpiredException) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
				navigationHandler.handleNavigation(facesContext, null, ((ViewExpiredException) rootCause).getViewId());
				facesContext.renderResponse();
			} else if (rootCause instanceof BadCredentialsException) {
				// This will be treated by the managed bean
				removeFromPile = false;
			} 
			if (removeFromPile) {
				i.remove();
				RequestContext contextPrimefaces = RequestContext.getCurrentInstance();
				contextPrimefaces.addCallbackParam("validationFailed", true);
			}
		}

		// At this point, the queue will not contain any ViewExpiredEvents nor
		// others already treated.
		// Therefore, let the parent handle them.
		getWrapped().handle();
	}

	@SuppressWarnings("unused")
	private void threatUnknownException(Throwable t) {
		if (isDebugMode) {
			FacesContext.getCurrentInstance().addMessage(null, MsgFactory.getMessage("message_unexpected_erro_debug", FacesMessage.SEVERITY_ERROR, t.toString()));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, MsgFactory.getMessage("message_unexpected_erro", FacesMessage.SEVERITY_ERROR, ""));
		}

		log.error("Um erro GRAVE ou NÃO ESPERADO aconteceu!", t);
	}
}