package org.basic.jdk.jdk7.e8_I18n_Unicode6;

/**
 * <pre>
 * 
 * Unicode 6.0
 * Upgrade the supported version of Unicode to 6.0
 * Lead: Yuka Kamiya
 * Spec: Unicode 6.0; java.lang.Character
 * Milestone target: M11
 * Locale enhancement
 * Upgrade the java.util.Locale class to support IETF BCP 47 (Tags for Identifying Languages) and UTR 35 (Local Data Markup Language)
 * Spec: IETF BCP 47 : java.util.Locale: forLanguageTag, toLanguageTag; UTR 35 : java.util.Locale: getUnicodeLocaleAttributes, getUnicodeLocaleType, getUnicodeLocaleKeys
 * Links: overview
 * Milestone target: M11
 * Separate user locale and user-interface locale
 * Upgrade the handling of locales to separate formatting locales from user-interface language locales
 * Spec: java.util.Locale: getDefault, setDefault; Locale.Category
 * Milestone target: M11
 * 
 * 
 * 8. Internationalization 增强 增加了对一些编码的支持和增加了一些显示方面的编码设置等
 *     
 *     1. New Scripts and Characters from Unicode 6.0.0
 *     2. Extensible Support for ISO 4217 Currency Codes
 *     Currency类添加：      
 *            getAvailableCurrencies 
 *            getNumericCode 
 *            getDisplayName 
 *            getDisplayName(Locale)
 *     3. Category Locale Support
 *      getDefault(Locale.Category)FORMAT  DISPLAY 
 *     4. Locale Class Supports BCP47 and UTR35
 *            UNICODE_LOCALE_EXTENSION
 *            PRIVATE_USE_EXTENSION
 *            Locale.Builder 
 *            getExtensionKeys()
 *            getExtension(char)
 *            getUnicodeLocaleType(String
 *             ……
 *     5. New NumericShaper Methods
 *     NumericShaper.Range 
 *     getShaper(NumericShaper.Range) 
 *     getContextualShaper(Set<NumericShaper.Range>)……
 * 
 * </pre>
 */
public class InternationalizationEnhance {

}
