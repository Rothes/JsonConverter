# JsonConverter
用于 Deltarune 本地化. 主要用于从各处提取英文本地化文本, 并将其转换至日文语言 json 中. 

## 特性
- 支持通过导出的 Codes 或 Strings 转换.
- 支持从英文语言 json 中获取部分键值.
- 支持从旧版本语言 json 升级.

## 用法
1. 获取 Deltarune 的任意版本.
2. 使用 [UndertaleModTool](https://github.com/krzys-h/UndertaleModTool) 导出所有 codes 或 strings.
3. 在命令行中定位到工具所在目录, 然后输入 `java -jar "json-coverter vxxx"` 并按下回车.
4. 按照提示选择相应转换方式, 输入需要的文件路径.
5. 等候转换完成.