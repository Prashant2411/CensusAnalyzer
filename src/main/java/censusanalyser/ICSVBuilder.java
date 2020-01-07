package censusanalyser;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder<E> {
    Iterator<E> getCSVFileIterable(Reader reader, Class<E> csvClass) throws CSVBuilderException;
}
