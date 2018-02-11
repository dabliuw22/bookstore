package com.leysoft.app.utilitys.converters.inter;

public interface Converter<T1, T2> {
	
	public T1 modelToEntity(T2 model);
	
	public T2 entityToModel(T1 entity);
}