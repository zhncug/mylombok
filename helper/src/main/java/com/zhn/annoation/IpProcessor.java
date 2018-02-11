package com.zhn.annoation;

/**
 * Created by nan.zhang on 18-2-11.
 */

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

/**
 * Created by nan.zhang on 18-2-8.
 */
@SupportedAnnotationTypes({
        "com.zhn.annoation.IpInfo"
})
public class IpProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private Elements elements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        System.out.println("IpProcessor init");
//        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elements = processingEnvironment.getElementUtils();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("Processing " + annotations + roundEnv);
        messager.printMessage(Diagnostic.Kind.NOTE, "1111");
        for (Element element : roundEnv.getElementsAnnotatedWith(IpInfo.class)) {

            if (element.getKind() != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Can be applied to class.");
                return true;
            }

            TypeElement typeElement = (TypeElement) element;
            String className = typeElement.getSimpleName().toString()+"Test";
            String packageName = elements.getPackageOf(typeElement).getQualifiedName().toString();

            FieldSpec fieldSpec = FieldSpec.builder(TypeName.BOOLEAN, "isActive", Modifier.PUBLIC).build();

            TypeSpec navigatorClass = TypeSpec
                    .classBuilder(className)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addField(fieldSpec).build();
            /**
             * 3- Write generated class to a file
             */
            try {
                JavaFile.builder(packageName, navigatorClass).build().writeTo(System.out);
                JavaFile.builder(packageName, navigatorClass).build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}