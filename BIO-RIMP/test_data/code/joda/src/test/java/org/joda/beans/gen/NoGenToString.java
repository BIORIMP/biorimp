/*
 *  Copyright 2001-2014 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.beans.gen;

import java.util.Map;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Mock used for test toString generation.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition
public class NoGenToString extends DirectBean {

    /**
     * The value.
     */
    @PropertyDefinition
    private Object value;

    @Override
    public String toString() {
        return "NoGenToString";
    }

    //------------------------- AUTOGENERATED START -------------------------
    ///CLOVER:OFF
    /**
     * The meta-bean for {@code NoGenToString}.
     * @return the meta-bean, not null
     */
    public static NoGenToString.Meta meta() {
        return NoGenToString.Meta.INSTANCE;
    }

    static {
        JodaBeanUtils.registerMetaBean(NoGenToString.Meta.INSTANCE);
    }

    @Override
    public NoGenToString.Meta metaBean() {
        return NoGenToString.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the value.
     * @return the value of the property
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value.
     * @param value  the new value of the property
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Gets the the {@code value} property.
     * @return the property, not null
     */
    public final Property<Object> value() {
        return metaBean().value().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public NoGenToString clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            NoGenToString other = (NoGenToString) obj;
            return JodaBeanUtils.equal(getValue(), other.getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getValue());
        return hash;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code NoGenToString}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code value} property.
         */
        private final MetaProperty<Object> value = DirectMetaProperty.ofReadWrite(
                this, "value", NoGenToString.class, Object.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "value");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 111972721:  // value
                    return value;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends NoGenToString> builder() {
            return new DirectBeanBuilder<NoGenToString>(new NoGenToString());
        }

        @Override
        public Class<? extends NoGenToString> beanType() {
            return NoGenToString.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code value} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Object> value() {
            return value;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 111972721:  // value
                    return ((NoGenToString) bean).getValue();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 111972721:  // value
                    ((NoGenToString) bean).setValue((Object) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

    }

    ///CLOVER:ON
    //-------------------------- AUTOGENERATED END --------------------------
}
