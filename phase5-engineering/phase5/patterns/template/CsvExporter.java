package phase5.patterns.template;

/** CSV 导出：逗号分隔 */
public class CsvExporter extends DataExporter {

    @Override
    protected String convert(String raw) {
        return raw.replace(" ", ",");
    }

    @Override
    protected void write(String data) {
        System.out.println("[CSV] " + data);
    }

    @Override
    protected String formatName() {
        return "CSV";
    }
}
