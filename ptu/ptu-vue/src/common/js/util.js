import XLSX from 'xlsx'
export default {
  replaceTime(time) {
    return time.replace(/-/g, '').replace(/:/g, '').replace(' ', '')
  },

  isEmpty(array) {
    if (array !== undefined && array !== null && array.length > 0) {
      return false
    }
    return true
  },
  exportSpecialExcel(aoa, lineNum, fileName, cols) {
    const sheet = XLSX.utils.aoa_to_sheet(aoa)

    sheet['!cols'] = cols
    sheet['!merges'] = [
      // 设置A1-C1的单元格合并
      { s: { r: 0, c: 0 }, e: { r: 0, c: lineNum }}
    ]
    this.openDownloadDialog(this.sheet2blob(sheet), fileName)
  },
  /**
	 * 通用的打开下载对话框方法，没有测试过具体兼容性
	 * @param url 下载地址，也可以是一个blob对象，必选
	 * @param saveName 保存文件名，可选
	 */
  openDownloadDialog(url, saveName) {
    if (typeof url === 'object' && url instanceof Blob) {
      url = URL.createObjectURL(url) // 创建blob地址
    }
    const aLink = document.createElement('a')
    aLink.href = url
    aLink.download = saveName || '' // HTML5新增的属性，指定保存文件名，可以不要后缀，注意，file:///模式下不会生效
    let event
    if (window.MouseEvent) {
      event = new MouseEvent('click')
    } else {
      event = document.createEvent('MouseEvents')
      event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null)
    }
    aLink.dispatchEvent(event)
  },
  // 将一个sheet转成最终的excel文件的blob对象，然后利用URL.createObjectURL下载
  sheet2blob(sheet, sheetName) {
    sheetName = sheetName || 'sheet1'
    var workbook = {
      SheetNames: [sheetName],
      Sheets: {}
    }
    workbook.Sheets[sheetName] = sheet
    // 生成excel的配置项
    const wopts = {
      bookType: 'xlsx', // 要生成的文件类型
      bookSST: false, // 是否生成Shared String Table，官方解释是，如果开启生成速度会下降，但在低版本IOS设备上有更好的兼容性
      type: 'binary'
    }
    const wbout = XLSX.write(workbook, wopts)
    const blob = new Blob([s2ab(wbout)], { type: 'application/octet-stream' })
    // 字符串转ArrayBuffer
    function s2ab(s) {
      const buf = new ArrayBuffer(s.length)
      const view = new Uint8Array(buf)
      for (let i = 0; i !== s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF
      return buf
    }
    return blob
  }
}
