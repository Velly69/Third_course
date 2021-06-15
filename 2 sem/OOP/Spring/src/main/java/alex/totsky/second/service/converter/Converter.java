package alex.totsky.second.service.converter;

public interface Converter<R, W, E> {

    R entityToDto(E entity);

    E dtoToEntity(W dto);
}
