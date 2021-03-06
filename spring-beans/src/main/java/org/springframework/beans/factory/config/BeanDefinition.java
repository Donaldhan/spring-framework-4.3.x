/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.config;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.AttributeAccessor;

/**
 * A BeanDefinition describes a bean instance, which has property values,
 * constructor argument values, and further information supplied by
 * concrete implementations.
 *bean定义BeanDefinition描述一个bean实例，即属性值和构造参数，具体的实现提供的更多信息。
 * <p>This is just a minimal interface: The main intention is to allow a
 * {@link BeanFactoryPostProcessor} such as {@link PropertyPlaceholderConfigurer}
 * to introspect and modify property values and other bean metadata.
 *这个仅仅是一个最小化的接口，主要的作用是允许bean工厂后处理器，可以内省和修改属性值及其他bean元数据。
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @since 19.03.2004
 * @see ConfigurableListableBeanFactory#getBeanDefinition
 * @see org.springframework.beans.factory.support.RootBeanDefinition
 * @see org.springframework.beans.factory.support.ChildBeanDefinition
 */
public interface BeanDefinition extends AttributeAccessor, BeanMetadataElement {

	/**
	 * Scope identifier for the standard singleton scope: "singleton".
	 * <p>Note that extended bean factories might support further scopes.
	 * 标准单例作用域，拓展bean工厂也许支持更多的作用域。
	 * @see #setScope
	 */
	String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

	/**
	 * Scope identifier for the standard prototype scope: "prototype".
	 * <p>Note that extended bean factories might support further scopes.
	 * 标准原型作用域，拓展bean工厂也许支持更多的作用域。
	 * @see #setScope
	 */
	String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;


	/**
	 * Role hint indicating that a {@code BeanDefinition} is a major part
	 * of the application. Typically corresponds to a user-defined bean.
	 * 表示一个应用的主要组成部分，比如一个用户定义的bean。
	 */
	int ROLE_APPLICATION = 0;

	/**
	 * Role hint indicating that a {@code BeanDefinition} is a supporting
	 * part of some larger configuration, typically an outer
	 * {@link org.springframework.beans.factory.parsing.ComponentDefinition}.
	 * 表示一个支持一些比较大的配置的bean定义，如一个外部的组件定义，{@link org.springframework.beans.factory.parsing.ComponentDefinition}
	 * {@code SUPPORT} beans are considered important enough to be aware
	 * of when looking more closely at a particular
	 * {@link org.springframework.beans.factory.parsing.ComponentDefinition},
	 * but not when looking at the overall configuration of an application.
	 * 
	 */
	int ROLE_SUPPORT = 1;

	/**
	 * Role hint indicating that a {@code BeanDefinition} is providing an
	 * entirely background role and has no relevance to the end-user. This hint is
	 * used when registering beans that are completely part of the internal workings
	 * of a {@link org.springframework.beans.factory.parsing.ComponentDefinition}.
	 * 表示一个内部使用的注册的bean组件定义，与终端用户无关。
	 */
	int ROLE_INFRASTRUCTURE = 2;


	// Modifiable attributes

	/**
	 * Set the name of the parent definition of this bean definition, if any.
	 * 设置bean定义的父name
	 */
	void setParentName(String parentName);

	/**
	 * Return the name of the parent definition of this bean definition, if any.
	 * 获取bean定义的父name
	 */
	String getParentName();

	/**
	 * Specify the bean class name of this bean definition.
	 * <p>The class name can be modified during bean factory post-processing,
	 * typically replacing the original class name with a parsed variant of it.
	 * 设置bean定义的bean class name。在bean工厂后处理的过程中，类名可以被修改，典型的情况下，
	 * 将原始class的name，替换成一个可解析的变量。
	 * @see #setParentName
	 * @see #setFactoryBeanName
	 * @see #setFactoryMethodName
	 */
	void setBeanClassName(String beanClassName);

	/**
	 * Return the current bean class name of this bean definition.
	 * 获取当前bean定义的name。
	 * <p>Note that this does not have to be the actual class name used at runtime, in
	 * case of a child definition overriding/inheriting the class name from its parent.
	 * Also, this may just be the class that a factory method is called on, or it may
	 * even be empty in case of a factory bean reference that a method is called on.
	 * Hence, do <i>not</i> consider this to be the definitive bean type at runtime but
	 * rather only use it for parsing purposes at the individual bean definition level.
	 * 需要注意的时，在孩子定义重写或从父类继承的name的情况下，此方法可能返回的不是实际运行环境下的class的name。
	 * 在工厂bean引用方法调用的情况下，此方法仅仅返回工厂方法的调用者，也许为空。因此，此方法不能用于运行时环境下，获取bean的类型
	 * ，仅仅用于在bean定义的层面上解析的目的。
	 * @see #getParentName()
	 * @see #getFactoryBeanName()
	 * @see #getFactoryMethodName()
	 */
	String getBeanClassName();

	/**
	 * Override the target scope of this bean, specifying a new scope name.
	 * 重写bean定义的作用域。
	 * @see #SCOPE_SINGLETON
	 * @see #SCOPE_PROTOTYPE
	 */
	void setScope(String scope);

	/**
	 * Return the name of the current target scope for this bean,
	 * 获取bean的作用域
	 * or {@code null} if not known yet.
	 */
	String getScope();

	/**
	 * Set whether this bean should be lazily initialized.
	 * <p>If {@code false}, the bean will get instantiated on startup by bean
	 * factories that perform eager initialization of singletons.
	 * 设置是否懒加载，如果为false，在bean工厂启动的时候，将会执行单例bean的预初始化。
	 */
	void setLazyInit(boolean lazyInit);

	/**
	 * Return whether this bean should be lazily initialized, i.e. not
	 * eagerly instantiated on startup. Only applicable to a singleton bean.
	 * 返回bean是否为懒加载模式，不能用于在启动过程中的懒加载初始化，仅仅可以用于单例bean。
	 */
	boolean isLazyInit();

	/**
	 * Set the names of the beans that this bean depends on being initialized.
	 * The bean factory will guarantee that these beans get initialized first.
	 * 设置bean初始化时依赖的bean的name。bean工厂在初始化bean的时候 ，保证先初始化依赖的bean。
	 */
	void setDependsOn(String... dependsOn);

	/**
	 * Return the bean names that this bean depends on.
	 * 返回bean依赖的bean的name集。
	 */
	String[] getDependsOn();

	/**
	 * Set whether this bean is a candidate for getting autowired into some other bean.
	 * 设置bean是否可以作为其他bean的自动注入对象。
	 * <p>Note that this flag is designed to only affect type-based autowiring.
	 * It does not affect explicit references by name, which will get resolved even
	 * if the specified bean is not marked as an autowire candidate. As a consequence,
	 * autowiring by name will nevertheless inject a bean if the name matches.
	 * 需要注意的是，此标志仅仅用于基于类型的自动注入。此方法不会显示地影响以name方法的bean的引用，以name方法引用的bean，
	 * 将会使用没有被标注为可自动注入的bean。因此依赖于name的自动注入，坚决不会注意name匹配的bean。
	 */
	void setAutowireCandidate(boolean autowireCandidate);

	/**
	 * Return whether this bean is a candidate for getting autowired into some other bean.
	 * 判断bean是否可以作为其他bean的自动注入对象
	 */
	boolean isAutowireCandidate();

	/**
	 * Set whether this bean is a primary autowire candidate.
	 * <p>If this value is {@code true} for exactly one bean among multiple
	 * matching candidates, it will serve as a tie-breaker.
	 * 设置bean是否为主要的自动注入候选者。如果多个类型匹配返回true，则以primary bean注入。
	 */
	void setPrimary(boolean primary);

	/**
	 * Return whether this bean is a primary autowire candidate.
	 * 判断bean是否为主要的自动注入候选者。
	 */
	boolean isPrimary();

	/**
	 * Specify the factory bean to use, if any.
	 * This the name of the bean to call the specified factory method on.
	 * 设置工厂bean的name，bean的name可以被工厂方法使用。
	 * @see #setFactoryMethodName
	 */
	void setFactoryBeanName(String factoryBeanName);

	/**
	 * Return the factory bean name, if any.
	 * 返回工厂bean的name
	 */
	String getFactoryBeanName();

	/**
	 * Specify a factory method, if any. This method will be invoked with
	 * constructor arguments, or with no arguments if none are specified.
	 * The method will be invoked on the specified factory bean, if any,
	 * or otherwise as a static method on the local bean class.
	 * 设置工厂bean方法。此方法调用将会使用构造参数，如果没有方法，则没有参数。
	 * 此方法将会被特定的工厂bean调用，或者本地bean类型的静态方法调用。
	 * 
	 * @see #setFactoryBeanName
	 * @see #setBeanClassName
	 */
	void setFactoryMethodName(String factoryMethodName);

	/**
	 * Return a factory method, if any.
	 * 获取工厂方法
	 */
	String getFactoryMethodName();

	/**
	 * Return the constructor argument values for this bean.
	 * 返回bean的构造参数值。
	 * <p>The returned instance can be modified during bean factory post-processing.
	 * 在bean工厂后处理的过程中，返回的实例可以本修改。
	 * @return the ConstructorArgumentValues object (never {@code null})
	 */
	ConstructorArgumentValues getConstructorArgumentValues();

	/**
	 * Return the property values to be applied to a new instance of the bean.
	 * <p>The returned instance can be modified during bean factory post-processing.
	 * 返回将要应用到bean新实例的属性值。在bean工厂后处理的过程中，返回的实例可以本修改。
	 * @return the MutablePropertyValues object (never {@code null})
	 */
	MutablePropertyValues getPropertyValues();


	// Read-only attributes

	/**
	 * Return whether this a <b>Singleton</b>, with a single, shared instance
	 * returned on all calls.
	 * 判断bean是否为单例共享实例。
	 * @see #SCOPE_SINGLETON
	 */
	boolean isSingleton();

	/**
	 * Return whether this a <b>Prototype</b>, with an independent instance
	 * returned for each call.
	 * 判断bean是否为原型bean实例。
	 * @since 3.0
	 * @see #SCOPE_PROTOTYPE
	 */
	boolean isPrototype();

	/**
	 * Return whether this bean is "abstract", that is, not meant to be instantiated.
	 * 判断bean是否为抽象类，意味着不可实例化
	 */
	boolean isAbstract();

	/**
	 * Get the role hint for this {@code BeanDefinition}. The role hint
	 * provides the frameworks as well as tools with an indication of
	 * the role and importance of a particular {@code BeanDefinition}.
	 * 获取bean定义的角色。
	 * @see #ROLE_APPLICATION
	 * @see #ROLE_SUPPORT
	 * @see #ROLE_INFRASTRUCTURE
	 */
	int getRole();

	/**
	 * Return a human-readable description of this bean definition.
	 * 返回bean定义的描述
	 */
	String getDescription();

	/**
	 * Return a description of the resource that this bean definition
	 * came from (for the purpose of showing context in case of errors).
	 * 返回bean定义资源的描述
	 */
	String getResourceDescription();

	/**
	 * Return the originating BeanDefinition, or {@code null} if none.
	 * 返回原始的bean定义，如果没有，则为null。
	 * Allows for retrieving the decorated bean definition, if any.
	 * <p>Note that this method returns the immediate originator. Iterate through the
	 * originator chain to find the original BeanDefinition as defined by the user.
	 * 此方法将返回即时的originator，通过迭代originator链，可以获取用户定义的原始bean定义。
	 */
	BeanDefinition getOriginatingBeanDefinition();

}
