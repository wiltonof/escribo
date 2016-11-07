package com.escribo.common.util;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

public class AjaxTimeoutPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 2639152532235352192L;

    @Override
    public void afterPhase(PhaseEvent ev) {
    }

    @Override
    public void beforePhase(PhaseEvent ev) {
        FacesContext fc = FacesUtils.getContext();
        RequestContext rc = RequestContext.getCurrentInstance();
        HttpServletResponse response = FacesUtils.getResponse();
        HttpServletRequest request = FacesUtils.getRequest();

        if (FacesUtils.getExternalContext().getUserPrincipal() == null) {
            if (FacesUtils.getExternalContext().isResponseCommitted()) {
                return;
            }
            try {
                if (((rc != null && rc.isAjaxRequest())
                        || (fc != null && fc.getPartialViewContext().isPartialRequest()))
                        && fc.getResponseWriter() == null && fc.getRenderKit() == null) {

                    response.setCharacterEncoding(request.getCharacterEncoding());
                    RenderKitFactory factory = (RenderKitFactory) FactoryFinder
                            .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
                    RenderKit renderKit = factory.getRenderKit(fc,
                            fc.getApplication().getViewHandler().calculateRenderKitId(fc));
                    ResponseWriter responseWriter = renderKit.createResponseWriter(response.getWriter(), null,
                            request.getCharacterEncoding());
                    responseWriter = new PartialResponseWriter(responseWriter);
                    fc.setResponseWriter(responseWriter);

                    FacesUtils.redirect("/login.xhtml");
                }
            } catch (IOException ex) {
                StringBuilder error = new StringBuilder("Redirect to the specified page '");
                error.append("/login.xhtml");
                error.append("' failed");

                throw new FacesException(ex);
            }
        } else {
            return; 
        }
    }

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}