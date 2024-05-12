package za.com.cocamzansi.util.localization.resolver;

import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class HttpHeaderLocaleResolver extends AcceptHeaderLocaleResolver {
    @Override
    public Locale resolveLocale(final HttpServletRequest request) {
        final String acceptLanguage = request.getHeader("Accept-Language");

        Locale locale;
        if (acceptLanguage != null && !acceptLanguage.isEmpty()) {
            locale = Locale.forLanguageTag(acceptLanguage);

            if (locale != null) {
                return locale;
            }
        }

        return Locale.ENGLISH; // default.
    }
}
