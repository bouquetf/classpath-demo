package fr.sewatech.classpath;

import fr.sewatech.classloader.MavenRepositoryClassLoader;
import fr.sewatech.classloader.MavenRepositoryLocalFirstClassLoader;

import java.lang.reflect.Method;

public class HelloViaMvnRepo {
    public static void main(String[] args) throws Exception {
        String version;
        if (args.length == 0) {
            System.out.println("Quelle version ?");
            return;
        } else {
            version = args[0];
        }

        String[] artefacts = {"fr.sewatech.conference:message-main:" + version,
                "fr.sewatech.conference:message-common:" + version,
                "fr.sewatech.conference:message-printer:" + version,
                "org.slf4j:slf4j-api:1.5.11"};
        ClassLoader classLoader;
        if (args.length > 1 && args[1].equals("local")) {
            classLoader = new MavenRepositoryLocalFirstClassLoader(artefacts);
        } else {
            classLoader = new MavenRepositoryClassLoader(artefacts);
        }

        Class<?> mainClass = Class.forName("fr.sewatech.message.Main",
                                        true,
                                        classLoader);
        Method mainMethod = mainClass.getDeclaredMethod("hello", boolean.class);
        mainMethod.invoke(null, false);
    }
}
