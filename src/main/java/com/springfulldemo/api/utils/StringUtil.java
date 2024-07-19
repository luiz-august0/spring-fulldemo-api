package com.springfulldemo.api.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.StringUtils;

import javax.swing.text.MaskFormatter;
import java.text.*;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class StringUtil {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty() || str.equalsIgnoreCase("null");
    }

    public static boolean isNullOrEmpty(Object str) {
        return str == null || str.toString().trim().isEmpty();
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static String toLowerCase(String text) {
        if (isNotNullOrEmpty(text)) {
            return text.toLowerCase();
        }
        return null;
    }

    public static String toStringNotNull(Object value) {
        return value != null ? value.toString() : "";
    }

    public static String toString(Object value) {
        return value != null ? value.toString() : null;
    }

    public static boolean isValidEmail(String email) {
        if (isNotNullOrEmpty(email)) {
            String regex = "[a-zA-Z0-9-_\\.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+";

            return Pattern.matches(regex, email);
        }
        return true;
    }

    public static String formatMessage(String messagePattern, Object... argumentsMessage) {
        return MessageFormat.format(messagePattern, argumentsMessage);
    }

    public static String keepOnlyNumbers(String s) {
        if (s == null) {
            return null;
        } else {
            return s.replaceAll("\\D", "");
        }
    }

    public static String firstWord(String texto) {
        if (StringUtil.isNotNullOrEmpty(texto)) {
            final StringTokenizer tokenizer = new StringTokenizer(texto);
            final String text = tokenizer.nextToken().toString();
            if (text.length() < 4 && tokenizer.hasMoreTokens()) {
                return text + " " + tokenizer.nextToken();
            }
            return text;
        }

        return texto;
    }

    public static String normalize(String value) {
        if (value == null) {
            return "";
        }
        value = value.trim();
        value = value.replaceAll("[^\\p{L}\\p{N}]", " ").replaceAll("[ ]+", "-");
        value = Normalizer.normalize(value, Normalizer.Form.NFD);
        value = value.replaceAll("[^\\p{ASCII}]", "");

        if (value.endsWith("-")) {
            value.subSequence(0, value.length() - 1);
        }
        value = value.replaceAll("[^\\p{ASCII}]", "");
        return value;
    }

    public static String randomIdentifier(final Integer qtd, final Boolean caseSensitive) {
        final String lexicon = caseSensitive == null || !caseSensitive ? "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890" : "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890abcdefghijklmnopqrstuvxyz";
        final java.util.Random rand = new java.util.Random();
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = qtd != null ? qtd : rand.nextInt(5) + 5;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
        }
        return builder.toString();
    }

    public static String capitalize(String value) {
        if (StringUtil.isNotNullOrEmpty(value)) {
            value = value.trim().toLowerCase();
            if (value.contains(" ")) {
                return Stream.of(value.split(" "))
                        .map(StringUtils::capitalize)
                        .collect(Collectors.joining(" ", "", ""));
            } else {
                return StringUtils.capitalize(value);
            }
        }
        return null;
    }

    public static String capitalizeAndShorthand(final String value) {
        final String capitalize = StringUtils.capitalize(value);
        return StringUtil.firstWord(capitalize);
    }

    public static String formataMoeda(Number value) {
        String formattedValue;
        NumberFormat formatter = new DecimalFormat("#,##0.00");

        try {
            formattedValue = formatter.format(value);
        } catch (IllegalArgumentException ex) {
            log.error(ex);
            formattedValue = formatter.format(0.0d);
        }

        return formattedValue;
    }

    public static String listToString(List list, String delimiter, String prefix, String suffix) {
        String result = (String) list.stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining(delimiter, prefix, suffix));
        return result;
    }

    public static String mapToString(Map<?, ?> map, String delimiter, String prefix, String suffix) {
        String result = map.entrySet()
                .stream()
                .map(e -> e.getValue().toString())
                .collect(Collectors.joining(delimiter, prefix, suffix));
        return result;
    }

    public static String formatDDDAndNumber(String phone) {
        keepOnlyNumbers(phone);

        if (isNotNullOrEmpty(phone)) {
            if (phone.length() > 3) {
                return "(" + phone.substring(0, 2) + ") " + phone.substring(2, phone.length());
            } else
                return phone;
        } else
            return "";

    }

    public static String formatCpfOrCnpj(String cpfCnpj) {
        if (isNotNullOrEmpty(cpfCnpj)) {

            String valueFormatted = cpfCnpj.trim();

            if (Utils.equals(valueFormatted.length(), 14)) {
                return formatCnpj(valueFormatted);
            }
            if (Utils.equals(valueFormatted.length(), 11)) {
                return formatCpf(valueFormatted);
            }
            return cpfCnpj;
        }
        return "";
    }

    private static String formatCpf(String cpf) {
        String cpfFormatted = "";
        try {
            MaskFormatter mask = new MaskFormatter("###.###.###-##");
            mask.setValueContainsLiteralCharacters(false);
            cpfFormatted = mask.valueToString(cpf);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return cpfFormatted;
    }

    private static String formatCnpj(String cnpj) {
        String cnpjFormatted = "";
        try {
            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            cnpjFormatted = mask.valueToString(cnpj);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return cnpjFormatted;
    }

    public static String encodeHtml(String str) {
        return StringEscapeUtils.escapeHtml4(str);
    }

    public static String getSubString(String texto, int tamanho) {
        if (StringUtil.isNullOrEmpty(texto)) {
            return texto;
        }

        texto = texto.trim();

        if (texto.length() > tamanho) {
            return texto.substring(0, tamanho);
        }

        return texto;
    }

    public static boolean isValidEmailAddress(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }

    public static String removeAccents(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static String replaceAccentsHtml(String html) {
        if (html != null) {
            html = html.replaceAll("Á", "&Aacute;");
            html = html.replaceAll("á", "&aacute;");
            html = html.replaceAll("Â", "&Acirc;");
            html = html.replaceAll("â", "&acirc;");
            html = html.replaceAll("À", "&Agrave;");
            html = html.replaceAll("à", "&agrave;");
            html = html.replaceAll("Ã", "&Atilde;");
            html = html.replaceAll("ã", "&atilde;");
            html = html.replaceAll("É", "&Eacute;");
            html = html.replaceAll("é", "&eacute;");
            html = html.replaceAll("Ê", "&Ecirc;");
            html = html.replaceAll("ê", "&ecirc;");
            html = html.replaceAll("È", "&Egrave;");
            html = html.replaceAll("è", "&egrave;");
            html = html.replaceAll("Ë", "&Euml;");
            html = html.replaceAll("ë", "&euml;");
            html = html.replaceAll("Í", "&Iacute;");
            html = html.replaceAll("í", "&iacute;");
            html = html.replaceAll("Î", "&Icirc;");
            html = html.replaceAll("î", "&icirc;");
            html = html.replaceAll("Ì", "&Igrave;");
            html = html.replaceAll("ì", "&igrave;");
            html = html.replaceAll("Ï", "&Iuml;");
            html = html.replaceAll("ï", "&iuml;");
            html = html.replaceAll("Ó", "&Oacute;");
            html = html.replaceAll("ó", "&oacute;");
            html = html.replaceAll("Ô", "&Ocirc;");
            html = html.replaceAll("ô", "&ocirc;");
            html = html.replaceAll("Õ", "&Otilde;");
            html = html.replaceAll("õ", "&otilde;");
            html = html.replaceAll("Ú", "&Uacute;");
            html = html.replaceAll("ú", "&uacute;");
            html = html.replaceAll("Ù", "&Ugrave;");
            html = html.replaceAll("ù", "&ugrave;");
            html = html.replaceAll("Ç", "&Ccedil;");
            html = html.replaceAll("ç", "&ccedil;");
            html = html.replaceAll("º", "&deg;");
            html = html.replaceAll("©", "&copy;");
            html = html.replaceAll("®", "&reg;");
        }
        return html;
    }

    public String formatDDDOnly(String phone) {
        keepOnlyNumbers(phone);

        if (isNotNullOrEmpty(phone)) {
            if (phone.length() >= 2) {
                return phone.substring(0, 1);
            } else
                return phone;
        } else
            return "";
    }
}
