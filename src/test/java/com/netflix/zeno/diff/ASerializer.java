/*
 *
 *  Copyright 2013 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package com.netflix.zeno.diff;

import java.util.Collection;
import java.util.Set;

import com.netflix.zeno.fastblob.record.schema.FastBlobSchema;
import com.netflix.zeno.serializer.NFDeserializationRecord;
import com.netflix.zeno.serializer.NFSerializationRecord;
import com.netflix.zeno.serializer.NFTypeSerializer;
import com.netflix.zeno.serializer.common.SetSerializer;

public class ASerializer extends NFTypeSerializer<TypeA>{

    private final FastBlobSchemaField[] fields = new FastBlobSchemaField[] {
            field("typeBs", new SetSerializer<TypeB>(new BSerializer()))
    };

    public ASerializer() {
        super("TypeA");
    }

    @Override
    public void doSerialize(TypeA value, NFSerializationRecord rec) {
        serializeObject(rec, "typeBs", value.getTypeBs());
    }

    @Override
    protected TypeA doDeserialize(NFDeserializationRecord rec) {
        Set<TypeB> setOfTypeBs = deserializeObject(rec, "typeBs");
        return new TypeA(setOfTypeBs);
    }

    @Override
    protected FastBlobSchema createSchema() {
        return schema(fields);
    }

    @Override
    public Collection<NFTypeSerializer<?>> requiredSubSerializers() {
        return requiredSubSerializers(fields);
    }
}
