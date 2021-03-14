/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a component is only eligible for registration when all
 * {@linkplain #value specified conditions} match.
 *
 *  指定的组件只有在所有的条件都匹配的情况下才会被注册到容器中
 *
 * <p>A <em>condition</em> is any state that can be determined programmatically
 * before the bean definition is due to be registered (see {@link Condition} for details).
 *
 *condition可以是任何可以被编程时定义的状态，在他的bd被注册的时候
 *
 * <p>The {@code @Conditional} annotation may be used in any of the following ways:
 * @Conditional注解可以一下几种方式使用
 * <ul>
 * <li>as a type-level annotation on any class directly or indirectly annotated with
 * {@code @Component}, including {@link Configuration @Configuration} classes</li>
 * <li>as a meta-annotation, for the purpose of composing custom stereotype annotations
 * annotations</li>
 *
 * 直接或间接注释在标记有@Component、@Configuration的类上，作为元注解，可以被组合定制
 *
 * <li>as a method-level annotation on any {@link Bean @Bean} method</li>
 * </ul>
 *
 * 定义在标记有@Bean的方法上
 *
 * <p>If a {@code @Configuration} class is marked with {@code @Conditional},
 * all of the {@code @Bean} methods, {@link Import @Import} annotations, and
 * {@link ComponentScan @ComponentScan} annotations associated with that
 * class will be subject to the conditions.
 *
 * 如果@Configuration标记的类被@Conditional标记，它的所有@Bean方法，@Import注解和@ComponentScan注解都将与其进行关联
 *
 * <p><strong>NOTE</strong>: Inheritance of {@code @Conditional} annotations
 * is not supported; any conditions from superclasses or from overridden
 * methods will not be considered. In order to enforce these semantics,
 * {@code @Conditional} itself is not declared as
 * {@link java.lang.annotation.Inherited @Inherited}; furthermore, any
 * custom <em>composed annotation</em> that is meta-annotated with
 * {@code @Conditional} must not be declared as {@code @Inherited}.
 *
 * 注意：不支持继承@Conditional注解；所有通过继承父类或者通过重写@Conditional注解的方式都会被忽略。
 * 为了加强语义，@Conditional自己没有被定义成可继承的；此外，任何自定义的组合注解如果包含的@Conditional必须不能被标记为可继承的(@Inherited)
 *
 * @author Phillip Webb
 * @author Sam Brannen
 * @since 4.0
 * @see Condition
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {

	/**
	 * All {@link Condition Conditions} that must {@linkplain Condition#matches match}
	 * in order for the component to be registered.
	 * 所有的Condition接口的match方法必须返回true，这个组件才会被注册到容器中
	 */
	Class<? extends Condition>[] value();

}
