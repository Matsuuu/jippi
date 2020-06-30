package matsu.jippi.pojo.common;

import java.util.List;
import java.util.Map;

public class ConversionByPlayerByOpening {
    private Map<String, Map<String, List<ConversionType>>> conversion;

    public Map<String, Map<String, List<ConversionType>>> getConversion() {
        return conversion;
    }

    public void setConversion(Map<String, Map<String, List<ConversionType>>> conversion) {
        this.conversion = conversion;
    }

    public ConversionByPlayerByOpening(Map<String, Map<String, List<ConversionType>>> conversion) {
        this.conversion = conversion;
    }

}
