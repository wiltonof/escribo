package com.escribo.common.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(
		{ 
			"classpath:i18n/messages_pt.properties"
		}
	)	
public class MsgFactory {

	private static String DEFAULT_DETAIL_SUFFIX = "_detail";
	private static String DEFAULT_LABEL_PREFIX = "label_";

	private MsgFactory() {
	}

	public static FacesMessage getMessage(final Locale locale, final String messageId, final FacesMessage.Severity severity, final Object... params) {
		final FacesMessage facesMessage = getMessage(locale, messageId, params);
		facesMessage.setSeverity(severity);
		return facesMessage;
	}

	public static FacesMessage getMessage(final Locale locale, final String messageId, final Object... params) {
		String summary = null;
		String detail = null;
		final FacesContext context = FacesContext.getCurrentInstance();
		final ResourceBundle bundle = context.getApplication().getResourceBundle(context, "messages");

		try {
			if (params != null) {
				for (int count = 0; count < params.length; count++) {
					if (((String) params[count]).startsWith(DEFAULT_LABEL_PREFIX)) {
						params[count] = getFormattedText(locale, bundle.getString((String) params[count]), null);
					}
				}
			}

			summary = getFormattedText(locale, bundle.getString(messageId), params);
		} catch (final MissingResourceException e) {
			summary = messageId;
		}

		try {
			detail = getFormattedText(locale, bundle.getString(messageId + DEFAULT_DETAIL_SUFFIX), params);
		} catch (final MissingResourceException e) {
			// NoOp
		}

		return new FacesMessage(summary, detail);
	}

	public static FacesMessage getMessage(final String messageId, final FacesMessage.Severity severity, final Object... params) {
		final FacesMessage facesMessage = getMessage(getLocale(), messageId, params);
		facesMessage.setSeverity(severity);
		return facesMessage;
	}

	public static FacesMessage getMessage(final String messageId, final Object... params) {
		return getMessage(getLocale(), messageId, params);
	}

	private static String getFormattedText(final Locale locale, final String message, final Object params[]) {
		MessageFormat messageFormat = null;

		if (params == null || message == null) {
			return message;
		}

		messageFormat = locale == null ? new MessageFormat(message) : new MessageFormat(message, locale);
		return messageFormat.format(params);
	}

	private static Locale getLocale() {
		Locale locale = null;
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null && facesContext.getViewRoot() != null) {
			locale = facesContext.getViewRoot().getLocale();
			if (locale == null) {
				locale = Locale.getDefault();
			}
		} else {
			locale = Locale.getDefault();
		}

		return locale;
	}

	public String resolveMessage(String messageId, String param, String param2) {
		String detail = null;
		final FacesContext context = FacesContext.getCurrentInstance();
		final ResourceBundle bundle = context.getApplication().getResourceBundle(context, "messages");
		String[] params = null;
		if (param != null && !param.equals("")) {
			if (param2 != null && !param2.equals("")) {
				params = new String[2];
				params[0] = param;
				params[1] = param2;
			} else {
				params = new String[1];
				params[0] = param;
			}
		}

		try {
			detail = bundle.getString(messageId);
			if (params != null) {
				for (int count = 0; count < params.length; count++) {
					detail = detail.replace("{" + count + "}", params[count]);
				}
			}
		} catch (final MissingResourceException e) {
			detail = messageId;
		}

		return detail;
	}

	public String resolveMessage(String messageId) {
		return resolveMessage(messageId, "", "");
	}

	public String resolveMessage(String messageId, String param) {
		return resolveMessage(messageId, param, "");
	}
}
