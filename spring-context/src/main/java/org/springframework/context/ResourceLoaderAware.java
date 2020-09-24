/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.context;

import org.springframework.beans.factory.Aware;
import org.springframework.core.io.ResourceLoader;

/**
 * Interface to be implemented by any object that wishes to be notified of
 * the <b>ResourceLoader</b> (typically the ApplicationContext) that it runs in.
 * This is an alternative to a full ApplicationContext dependency via the
 * ApplicationContextAware interface.
 * 任何希望得到资源加载器实现的接口，比如应用上下文。这是通过 ApplicationContextAware接口
 * 获取应用上下文的另一个选择。
 *
 *
 * <p>Note that Resource dependencies can also be exposed as bean properties
 * of type Resource, populated via Strings with automatic type conversion by
 * the bean factory. This removes the need for implementing any callback
 * interface just for the purpose of accessing a specific file resource.
 *
 * 注意资源依赖可以作为类型资源的bean属性，进行暴露，通常通过bean工厂，进行字符串自动转换为相应的类型。
 * 移除任何回调的接口，仅仅为方位特殊的文件资源
 *
 * <p>You typically need a ResourceLoader when your application object has
 * to access a variety of file resources whose names are calculated. A good
 * strategy is to make the object use a DefaultResourceLoader but still
 * implement ResourceLoaderAware to allow for overriding when running in an
 * ApplicationContext. See ReloadableResourceBundleMessageSource for an example.
 * 当你的应用对象不得不访问一个合适的文件资源变体事，你需要一个资源加载器。 一个好的策略是，
 * 使用DefaultResourceLoader加载一个对象，而不是实现ResourceLoaderAware，运行它重新应用上下文。
 * 具体看ReloadableResourceBundleMessageSource
 *
 * <p>A passed-in ResourceLoader can also be checked for the
 * <b>ResourcePatternResolver</b> interface and cast accordingly, to be able
 * to resolve resource patterns into arrays of Resource objects. This will always
 * work when running in an ApplicationContext (the context interface extends
 * ResourcePatternResolver). Use a PathMatchingResourcePatternResolver as default.
 * See also the {@code ResourcePatternUtils.getResourcePatternResolver} method.
 * 通过passed-in可以检查ResourcePatternResolver， 也可以解决资源数组中的资源模式。这对于
 * 实现ResourcePatternResolver的应用上下文始终有效。默认用PathMatchingResourcePatternResolver。
 * 也可以用{@code ResourcePatternUtils.getResourcePatternResolver}的方法
 * <p>As alternative to a ResourcePatternResolver dependency, consider exposing
 * bean properties of type Resource array, populated via pattern Strings with
 * automatic type conversion by the bean factory.
 * 作为ResourcePatternResolver依赖的另一种选择，考虑通过bean工厂的自动类型转换字符串，
 * 暴露类型资源数组的bean属性。
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 10.03.2004
 * @see ApplicationContextAware
 * @see org.springframework.beans.factory.InitializingBean
 * @see org.springframework.core.io.Resource
 * @see org.springframework.core.io.support.ResourcePatternResolver
 * @see org.springframework.core.io.support.ResourcePatternUtils#getResourcePatternResolver
 * @see org.springframework.core.io.DefaultResourceLoader
 * @see org.springframework.core.io.support.PathMatchingResourcePatternResolver
 * @see org.springframework.context.support.ReloadableResourceBundleMessageSource
 */
public interface ResourceLoaderAware extends Aware {

	/**
	 * Set the ResourceLoader that this object runs in.
	 * <p>This might be a ResourcePatternResolver, which can be checked
	 * through {@code instanceof ResourcePatternResolver}. See also the
	 * {@code ResourcePatternUtils.getResourcePatternResolver} method.
	 * <p>Invoked after population of normal bean properties but before an init callback
	 * like InitializingBean's {@code afterPropertiesSet} or a custom init-method.
	 * Invoked before ApplicationContextAware's {@code setApplicationContext}.
	 * @param resourceLoader ResourceLoader object to be used by this object
	 * @see org.springframework.core.io.support.ResourcePatternResolver
	 * @see org.springframework.core.io.support.ResourcePatternUtils#getResourcePatternResolver
	 */
	void setResourceLoader(ResourceLoader resourceLoader);

}
