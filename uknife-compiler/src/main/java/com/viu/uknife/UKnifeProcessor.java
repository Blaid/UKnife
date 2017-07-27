package com.viu.uknife;

import com.viu.uknife.visitors.BindViewVisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Element;

@SupportedAnnotationTypes({ "com.viu.uknife.BindView" })
public class UKnifeProcessor extends AbstractProcessor {
  private final Map<TypeElement, BindViewVisitor> visitors = new HashMap<>();

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (annotations.isEmpty()) {
      return false;
    }

    final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindView.class);
    for (final Element element : elements) {
      final TypeElement object = (TypeElement) element.getEnclosingElement();
      BindViewVisitor visitor = visitors.get(object);
      if (visitor == null) {
        visitor = new BindViewVisitor(processingEnv, object);
        visitors.put(object, visitor);
      }
      element.accept(visitor, null);
    }

    for (final BindViewVisitor visitor : visitors.values()) {
      visitor.brewJava();
    }

    return true;
  }

  @Override public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }
}
