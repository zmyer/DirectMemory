package org.directmemory.utils;

import java.io.IOException;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ProtoStuffSerializer implements Serializer {
	
	static int serBufferSize = 512;
	
	/* (non-Javadoc)
	 * @see org.directmemory.utils.Serializer#serialize(java.lang.Object, java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public byte[] serialize(Object obj, @SuppressWarnings("rawtypes") Class clazz) throws IOException {
		@SuppressWarnings("rawtypes")
		Schema schema = RuntimeSchema.getSchema(clazz);
		final LinkedBuffer buffer = LinkedBuffer.allocate(serBufferSize);
		byte[] protostuff = null;

		try {
			protostuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
		} finally {
			buffer.clear();
		}		

		return protostuff;
	}

	/* (non-Javadoc)
	 * @see org.directmemory.utils.Serializer#deserialize(byte[], java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object deserialize(byte[] source, @SuppressWarnings("rawtypes") Class clazz) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		final Object object = clazz.newInstance();
		@SuppressWarnings("rawtypes")
		Schema schema = RuntimeSchema.getSchema(clazz);
		ProtostuffIOUtil.mergeFrom(source, object, schema);
		return object;
	}
}