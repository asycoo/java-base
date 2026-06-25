package phase5.patterns.template;

/**
 * 模板方法 — 固定流程骨架，子类填具体步骤
 *
 * 场景：导出数据：校验 → 转换 → 写文件（步骤相同，格式不同）
 */
public abstract class DataExporter {

    /** 模板方法：流程固定，不允许子类改顺序 */
    public final void export(String raw) {
        validate(raw);
        String converted = convert(raw);
        write(converted);
        System.out.println("导出完成: " + formatName());
    }

    protected void validate(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("数据不能为空");
        }
    }

    protected abstract String convert(String raw);

    protected abstract void write(String data);

    protected abstract String formatName();
}
