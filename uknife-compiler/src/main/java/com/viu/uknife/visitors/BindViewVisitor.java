package com.viu.uknife.visitors;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.util.ElementScanner7;

/**
 * Created by vasilev.i.u on 26.07.2017.
 */
public class BindViewVisitor extends ElementScanner7<Void, Void> {
  private final CodeBlock.Builder findViewById = CodeBlock.builder();
}
