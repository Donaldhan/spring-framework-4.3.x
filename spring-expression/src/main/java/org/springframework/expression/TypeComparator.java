/*
 * Copyright 2002-2013 the original author or authors.
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

package org.springframework.expression;

/**
 * Instances of a type comparator should be able to compare pairs of objects for equality.
 * The specification of the return value is the same as for {@link java.lang.Comparable}.
 *类型比较器实例，应该可以比较对象pair的相等性。返回值与{@link java.lang.Comparable}相同。
 * @author Andy Clement
 * @since 3.0
 * @see java.lang.Comparable
 */
public interface TypeComparator {

	/**
	 * Return {@code true} if the comparator can compare these two objects.
	 * 如果两个对象可比较，则返回true
	 * @param firstObject the first object
	 * @param secondObject the second object
	 * @return {@code true} if the comparator can compare these objects
	 */
	boolean canCompare(Object firstObject, Object secondObject);

	/**
	 * Compare two given objects.
	 * 比较两个对象。
	 * @param firstObject the first object
	 * @param secondObject the second object
	 * @return 0 if they are equal, <0 if the first is smaller than the second,
	 * or >0 if the first is larger than the second
	 * @throws EvaluationException if a problem occurs during comparison
	 * (or if they are not comparable in the first place)
	 */
	int compare(Object firstObject, Object secondObject) throws EvaluationException;

}