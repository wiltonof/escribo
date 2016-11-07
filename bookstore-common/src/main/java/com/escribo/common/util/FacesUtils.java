package com.escribo.common.util;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;


public class FacesUtils {

 //   public static Logger logger = LoggerFactory.getLogger(FacesUtils.class);

    /**
     * Returns the current faces context.
     * <p>
     * <i>Note that whenever you absolutely need this method to perform a general task, you might want to consider to
     * submit a feature request to OmniFaces in order to add a new utility method which performs exactly this general
     * task.</i>
     * @return The current faces context.
     * @see FacesContext#getCurrentInstance()
     */
    public static FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Returns the HTTP servlet response.
     * <p>
     * <i>Note that whenever you absolutely need this method to perform a general task, you might want to consider to
     * submit a feature request to OmniFaces in order to add a new utility method which performs exactly this general
     * task.</i>
     * @return The HTTP servlet response.
     * @see ExternalContext#getResponse()
     */
    public static HttpServletResponse getResponse() {
        return getResponse(getContext());
    }

    /**
     * {@inheritDoc}
     * @see Faces#getResponse()
     */
    public static HttpServletResponse getResponse(FacesContext context) {
        return (HttpServletResponse) context.getExternalContext().getResponse();
    }

    /**
     * Returns the HTTP servlet request.
     * <p>
     * <i>Note that whenever you absolutely need this method to perform a general task, you might want to consider to
     * submit a feature request to OmniFaces in order to add a new utility method which performs exactly this general
     * task.</i>
     * @return The HTTP servlet request.
     * @see ExternalContext#getRequest()
     */
    public static HttpServletRequest getRequest() {
        return getRequest(getContext());
    }

    /**
     * {@inheritDoc}
     * @see Faces#getRequest()
     */
    public static HttpServletRequest getRequest(FacesContext context) {
        return (HttpServletRequest) context.getExternalContext().getRequest();
    }

    /**
     * Returns the current external context.
     * <p>
     * <i>Note that whenever you absolutely need this method to perform a general task, you might want to consider to
     * submit a feature request to OmniFaces in order to add a new utility method which performs exactly this general
     * task.</i>
     * @return The current external context.
     * @see FacesContext#getExternalContext()
     */
    public static ExternalContext getExternalContext() {
        return getContext().getExternalContext();
    }

    /**
     * Returns the HTTP request context path. It's the webapp context name, with a leading slash. If the webapp runs
     * on context root, then it returns an empty string.
     * @return The HTTP request context path.
     * @see ExternalContext#getRequestContextPath()
     */
    public static String getRequestContextPath() {
        return getRequestContextPath(getContext());
    }

    /**
     * {@inheritDoc}
     * @see Faces#getRequestContextPath()
     */
    public static String getRequestContextPath(FacesContext context) {
        return context.getExternalContext().getRequestContextPath();
    }

    /**
     * Does a regular or ajax redirect.
     */
    public static void redirect(String redirectPage) throws FacesException {
        checkViewRoot(FacesUtils.getContext(), FacesUtils.getRequestContextPath());

        FacesContext fc = FacesUtils.getContext();
        ExternalContext ec = fc.getExternalContext();

        try {
            if (ec.isResponseCommitted()) {
                // redirect is not possible
                return;
            }

            // fix for renderer kit (Mojarra's and PrimeFaces's ajax redirect)
            if ((RequestContext.getCurrentInstance().isAjaxRequest() || fc.getPartialViewContext().isPartialRequest())
                    && fc.getResponseWriter() == null && fc.getRenderKit() == null) {
                ServletResponse response = (ServletResponse) ec.getResponse();
                ServletRequest request = (ServletRequest) ec.getRequest();
                response.setCharacterEncoding(request.getCharacterEncoding());

                RenderKitFactory factory = (RenderKitFactory) FactoryFinder
                        .getFactory(FactoryFinder.RENDER_KIT_FACTORY);

                RenderKit renderKit = factory.getRenderKit(fc,
                        fc.getApplication().getViewHandler().calculateRenderKitId(fc));

                ResponseWriter responseWriter = renderKit.createResponseWriter(response.getWriter(), null,
                        request.getCharacterEncoding());
                fc.setResponseWriter(responseWriter);
            }

            ec.redirect(ec.getRequestContextPath() + (redirectPage != null ? redirectPage : ""));
        } catch (IOException e) {
 //           logger.error("Redirect to the specified page '" + redirectPage + "' failed");
            throw new FacesException(e);
        }
    }

    public static void checkViewRoot(FacesContext ctx, String viewId) {
        if (ctx.getViewRoot() == null) {
            UIViewRoot viewRoot = ctx.getApplication().getViewHandler().createView(ctx, viewId);
            if (viewRoot != null) {
                ctx.setViewRoot(viewRoot);
            }
        }
    }

}