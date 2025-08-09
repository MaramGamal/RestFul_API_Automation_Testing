package config;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
        private static Properties properties;

        static {
            try {
                // نفتح ملف الإعدادات
                FileInputStream input = new FileInputStream("src/main/resources/config.properties");

                // نحمّل الملف في Object من نوع Properties
                properties = new Properties();
                properties.load(input);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // دالة بتاخد اسم المفتاح وترجع قيمته
        public static String get(String key) {
            return properties.getProperty(key);
        }
}

