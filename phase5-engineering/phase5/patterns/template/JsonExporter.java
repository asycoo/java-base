package phase5.patterns.template;

/** JSON 导出：简单包一层引号 */
public class JsonExporter extends DataExporter {

    @Override
    protected String convert(String raw) {
        return "{\"data\":\"" + raw + "\"}";
    }

    @Override
    protected void write(String data) {
        System.out.println("[JSON] " + data);
    }

    @Override
    protected String formatName() {
        return "JSON";
    }
}
