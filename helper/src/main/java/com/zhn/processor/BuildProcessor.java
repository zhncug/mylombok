package com.zhn.processor;

import com.google.auto.service.AutoService;
import com.zhn.annoation.hibernate.Id;
import com.zhn.annoation.hibernate.Persistent;
import com.zhn.annoation.hibernate.Property;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.io.*;
import java.util.Set;

/**
 * @Author zhangnan
 * @Date 18-12-5
 **/
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes({"com.zhn.annoation.hibernate.Persistent",
        "com.zhn.annoation.hibernate.Id",
        "com.zhn.annoation.hibernate.Property"})
@AutoService(value = Processor.class)
public class BuildProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        System.out.println("BuildProcessor init");
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element t : roundEnv.getElementsAnnotatedWith(Persistent.class)) {
            Name clasName = t.getSimpleName();

            Persistent per = t.getAnnotation(Persistent.class);

            try (PrintStream ps = new PrintStream(new FileOutputStream(clasName + ".hbm.xml"))) {

                ps.println("<?xml version=\"1.0\"?>");
                ps.println("<!DOCTYPE hibernate-mapping PUBLIC");
                ps.println("    \"-//Hibernate/Hibernate " + "Mapping DTD 3.0//EN\"");
                ps.println("    \"http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd\">");
                ps.println("<hibernate-mapping>");
                ps.print("  <class name=\"" + t);
                ps.println("\" table=\"" + per.table() + "\">");
                for (Element f : t.getEnclosedElements()) {
                    if (f.getKind() == ElementKind.FIELD) {
                        Id id = f.getAnnotation(Id.class);

                        if (null != id) {
                            ps.println("     <id name=\"" + f.getSimpleName() + "\" column=\"" + id.column()
                                    + "\" type=\"" + id.type() + "\">");
                        }
                        Property p = f.getAnnotation(Property.class);

                        if (p != null) {
                            ps.println("         <property name=\"" + f.getSimpleName()
                                    + "\" column=\"" + p.column() + "\" type=\"" + p.type() + "\"/>");
                        }
                    }
                    ps.println("    </class>");
                    ps.println("</hibernate-mapping>");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
