package com.viu.uknife;

import com.google.auto.service.AutoService;
import com.viu.uknife.visitors.BindViewVisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Element;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.viu.uknife.BindView"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class UKnifeProcessor extends AbstractProcessor {
    private final Map<TypeElement, BindViewVisitor> visitors = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }

        final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindView.class);
        for(final Element element : elements) {
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();

            BindViewVisitor visitor = visitors.get(typeElement);
            if (visitor == null) {
                visitor = new BindViewVisitor();
                visitors.put(typeElement, visitor);
            }

            typeElement.accept(visitor, null);

            for (BindViewVisitor bindViewVisitor : visitors.values()) {
                bindViewVisitor.brewJava();
            }
        }

        return true;
    }
}
