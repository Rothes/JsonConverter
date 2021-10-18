# JsonConverter
用于 Deltarune 本地化。

## 用法
1. 获取 Deltarune 的任意版本.
2. 使用 [UndertaleModTool](https://github.com/krzys-h/UndertaleModTool) 导出所有 strings.
3. 在命令行中定位到工具所在目录, 然后输入 `java -jar "json-coverter vxxx"` 并按下回车.
4. 按照提示输入导出的 strings 文件路径和 Deltarune/lang 中的语言 json 文件路径.
5. 等候转换完成.

## 注意
- 有时由于游戏内另类的函数用法, 会导致 UndertaleModTool 导出的 strings 顺序不同, 转换出现错误. 此时仍需要手动获取英文文本. 之后会考虑通过导出所有 codes 并通过函数传参来获取英文文本.
