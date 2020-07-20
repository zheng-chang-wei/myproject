<template>
  <div class="result">
    <el-row id="data-result">
      <el-col :span="8">
        <el-row v-show="fileOrData" class="grid-content">
          <form ref="fileForm" action method="get" style="display:none">
            <input ref="input" type="text" name="filePath" value>
          </form>
          <el-form label-width="80px">
            <el-collapse @change="handleChange">
              <el-collapse-item title="信号分析数据文件选择" class="title">
                <el-form-item label="开始时间">
                  <el-date-picker
                    v-model="startDate"
                    class="dateGrp"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="开始时间"
                  />
                </el-form-item>
                <el-form-item label="结束时间">
                  <el-date-picker v-model="endDate" class="dateGrp" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="结束时间" />
                </el-form-item>
                <el-form-item label="项点">
                  <el-input v-model="name" placeholder="请输入项点" />
                </el-form-item>
                <el-form-item label="算法">
                  <el-input v-model="algorithm" placeholder="请输入算法" />
                </el-form-item>
                <el-form-item label="分系统">
                  <el-input v-model="subsystem" placeholder="请输入分系统" />
                </el-form-item>
              </el-collapse-item>
            </el-collapse>
            <el-button type="primary" style="width: 100%" size="mini" @click="getFileList(1)">搜索</el-button>
          </el-form>
          <div class="treeDiv">
            <el-table
              ref="mytable"
              :row-key="getRowKeys"
              :data="fileList"
              :row-style="{ height:'10px',fontSize: '13px' }"
              :header-cell-style="{padding:'0px',height:'20px'}"
              :cell-style="{padding:'0px'}"
              :height="tableHeight"
              border
              @selection-change="handleSelectionChange"
            >
              <el-table-column type="selection" :reserve-selection="ifReserve" width="40" />
              <el-table-column prop="time" label="日期时间" width="110" />
              <el-table-column prop="name" label="项点" />
              <el-table-column prop="algorithm" label="算法" />
              <el-table-column prop="subsystem" label="分系统" />
            </el-table>
          </div>
          <div class="btgrp" style="bottom: 45px;">
            <el-pagination
              background
              small
              layout="prev, pager, next"
              :page-size="pageSize"
              :total="total"
              :current-page="pageNum"
              @current-change="changPageNum"
            />
          </div>
          <div class="btgrp" style="margin-bottom:5px">
            <el-button type="primary" size="mini" @click="downloadResultFile">下载</el-button>
            <el-button type="success" size="mini" @click="getCheckedNodes()">查看</el-button>
            <el-button type="danger" size="mini" @click="delResultItems">删除</el-button>
          </div>
        </el-row>
        <el-row v-show="!fileOrData" class="grid-content">
          <switch-button @comChanged="comChanged($event)" />
          <el-form label-width="80px">
            <el-form-item label="信号名">
              <el-input v-model="input" clearable placeholder="请输入内容" />
            </el-form-item>
          </el-form>
          <el-button type="primary" style="width: 100%" size="mini" @click="signalFilter">搜索</el-button>
          <el-table
            v-show="window12==1"
            ref="multiSig1"
            :data="signalList"
            height="300"
            style="width: 100%"
            :row-style="{height:'30px',fontSize: '13px'}"
            :cell-style="{padding:'0px'}"
            @selection-change="countAndPush1"
          >
            <el-table-column type="selection" width="55" />
            <el-table-column label="信号名" prop="signalName" />
            <el-table-column label="变量名" prop="name" />
            <el-table-column label="变量类型" prop="type" />
          </el-table>
          <el-table
            v-show="window12==2"
            ref="multiSig2"
            :data="signalList"
            height="300"
            style="width: 100%"
            :row-style="{height:'30px',fontSize: '13px'}"
            :cell-style="{padding:'0px'}"
            @selection-change="countAndPush2"
          >
            <el-table-column type="selection" width="55" />
            <el-table-column label="信号名" prop="signalName" />
            <el-table-column label="变量名" prop="name" />
            <el-table-column label="变量类型" prop="type" />
          </el-table>
          <div class="btgrp">
            <el-button type="primary" style="width: 40%" size="mini" @click="fileOrData = true">返回</el-button>
            <el-button type="success" style="width: 40%" size="mini" @click="drawChart">绘图</el-button>
          </div>
        </el-row>
      </el-col>
      <el-col :span="16">
        <div v-show="dataOrVideo" class="chartDiv" :style="chartHeight">
          <chart ref="chart" />
        </div>
        <video-play v-show="!dataOrVideo" ref="videoPlsyer" :uri="videoUri" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import app from '@/common/js/app'
import SwitchButton from '@/components/Base/SwitchButton'
import chart from '../chart'
import videoPlay from './videoPlay'

export default {
  name: 'Index',
  components: { SwitchButton, chart, videoPlay },
  data() {
    return {
      tableHeight: 0,
      chartHeight: {},
      startDate: '',
      endDate: '',
      time: '',
      name: '',
      algorithm: '',
      subsystem: '',
      fileOrData: true,
      fileList: [],
      modeFlag: 'all',
      checkedFile: [],
      ifReserve: true,
      pageSize: 20,
      pageNum: 1,
      total: 20,
      window12: '1',
      sigSelShow: false,
      headersData: [],
      input: '',
      signalList: [],
      signalListBak: [],
      sigSelLimit: 5,
      sig1Seled: [],
      sig2Seled: [],
      ipAddress: '',
      videoUri: '',
      dataOrVideo: true
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  mounted() {
    this.getAllFileList(1)
  },
  methods: {
    // 分页 第一页 all
    getAllFileList(ifFirst) {
      if (ifFirst === 1) {
        this.pageNum = 1
        this.modeFlag = 'all'
      }
      const vm = this
      app
        .postData('result_all', {
          QueryRequest: {
            pageSize: this.pageSize,
            pageNum: this.pageNum
          }
        })
        .then(data => {
          if (data.code === 0) {
            vm.total = data.total
            vm.fileList = data.data
            for (let i = 0; i < vm.fileList.length; i++) {
              vm.fileList[i].time =
                vm.fileList[i].timestamp.substr(0, 10) +
                '\n' +
                vm.fileList[i].timestamp.substr(11, 8)
            }
          }
        })
    },
    // 分页 第一页 list
    getFileList(ifFirst) {
      if (
        this.startDate !== '' &&
        this.endDate !== '' &&
        this.startDate > this.endDate
      ) {
        this.$alert('结束时间必须大于等于开始时间', '提示信息', {
          confirmButtonText: '确定'
        })
        return
      }
      if (ifFirst === 1) {
        this.pageNum = 1
        this.modeFlag = 'list'
      }
      const vm = this
      app
        .postData('result_list', {
          QueryRequest: {
            pageSize: this.pageSize,
            pageNum: this.pageNum
          },
          ResultQueryParam: {
            start: this.startDate,
            end: this.endDate,
            name: this.name,
            algorithm: this.algorithm,
            subsystem: this.subsystem
          }
        })
        .then(data => {
          if (data.code === 0) {
            vm.total = data.total
            vm.fileList = data.data
            for (let i = 0; i < vm.fileList.length; i++) {
              vm.fileList[i].time =
                vm.fileList[i].timestamp.substr(0, 10) +
                '\n' +
                vm.fileList[i].timestamp.substr(11, 8)
            }
          }
        })
    },
    changPageNum(cpage) {
      this.pageNum = cpage
      if (this.modeFlag === 'all') {
        this.getAllFileList()
      } else {
        this.getFileList()
      }
    },
    handleSelectionChange(files) {
      this.checkedFile = files
    },
    getRowKeys(row) {
      return row.id
    },
    getCheckedNodes() {
      if (this.checkedFile.length >= 2) {
        this.$alert('针对分析数据解析结果只支持单选模式', '提示信息', {
          confirmButtonText: '确定'
        })
      } else if (this.checkedFile.length === 1) {
        if (this.checkedFile[0].fileType === 0) {
          this.dataOrVideo = true
          this.loadHeader(this.checkedFile[0].id)
        } else if (this.checkedFile[0].fileType === 1) {
          this.dataOrVideo = false
          this.loadVideo(this.checkedFile[0])
        }
      } else {
        this.$alert('没有选择数据文件', '提示信息', {
          confirmButtonText: '确定'
        })
      }
    },
    delResultItems() {
      const deleteNodes = this.checkedFile
      app.postData('result_delete', { deleteNodes: deleteNodes }).then(data => {
        if (data.code === 0) {
          this.$message({
            duration: 1500,
            showClose: true,
            type: 'success',
            message: data.data
          })
          this.$refs.mytable.clearSelection()
          this.getAllFileList()
        }
      })
    },
    loadHeader(resultId) {
      this.fileSelShow = false
      const vm = this
      app
        .postData('result_header', {
          resultId: resultId
        })
        .then(data => {
          if (data.code === 0) {
            vm.signalList = []
            vm.headersData = JSON.parse(JSON.stringify(data.data.headers))
            const tempType = vm.headersData.length > 0 ? vm.headersData[0].type : ''
            for (let i = 0; i < vm.headersData.length; i++) {
              for (let j = 0; j < vm.headersData[i].variables.length; j++) {
                vm.signalList.push(vm.headersData[i].variables[j])
              }
            }
            for (let i = 0; i < vm.signalList.length; i++) {
              vm.signalList[i].type = tempType
            }
            vm.signalListBak = JSON.parse(JSON.stringify(vm.signalList))
            vm.fileOrData = false
          }
        }).catch(() => {
        })
    },
    countAndPush1(val) {
      if (val.length > this.sigSelLimit) {
        this.$refs.multiSig1.toggleRowSelection(val[val.length - 1], false)
        this.$alert('同一窗口最多添加5个信号', '提示信息', {
          confirmButtonText: '确定'
        })
        return false
      } else if (val.length > 0) {
        const signalName = val[val.length - 1].signalName
        const name = val[val.length - 1].name
        const nowcomId = val[val.length - 1].comId
        const nowsourceIp = val[val.length - 1].sourceIp
        for (let i = 0; i < this.sig2Seled.length; i++) {
          if (
            signalName === this.sig2Seled[i].signalName &&
            name === this.sig2Seled[i].name &&
            nowcomId === this.sig2Seled[i].comId &&
            nowsourceIp === this.sig2Seled[i].sourceIp
          ) {
            this.$refs.multiSig1.toggleRowSelection(val[val.length - 1], false)
            this.$alert('此信号已经添加在窗口2', '提示信息', {
              confirmButtonText: '确定'
            })
            return false
          }
        }
        this.sig1Seled = val
      } else if (val.length === 0) {
        this.sig1Seled = val
      }
    },
    countAndPush2(val) {
      if (val.length > this.sigSelLimit) {
        this.$refs.multiSig2.toggleRowSelection(val[val.length - 1], false)
        this.$alert('同一窗口最多添加5个信号', '提示信息', {
          confirmButtonText: '确定'
        })
        return false
      } else if (val.length > 0) {
        const signalName = val[val.length - 1].signalName
        const name = val[val.length - 1].name
        const nowcomId = val[val.length - 1].comId
        const nowsourceIp = val[val.length - 1].sourceIp
        for (let i = 0; i < this.sig1Seled.length; i++) {
          if (
            signalName === this.sig1Seled[i].signalName &&
            name === this.sig1Seled[i].name &&
            nowcomId === this.sig1Seled[i].comId &&
            nowsourceIp === this.sig1Seled[i].sourceIp
          ) {
            this.$refs.multiSig2.toggleRowSelection(val[val.length - 1], false)
            this.$alert('此信号已经添加在窗口1', '提示信息', {
              confirmButtonText: '确定'
            })
            return false
          }
        }
        this.sig2Seled = val
      } else if (val.length === 0) {
        this.sig2Seled = val
      }
    },
    downloadResultFile() {
      const vm = this
      const selectedNodes = vm.checkedFile
      if (selectedNodes.length === 0) {
        this.$alert('没有选择文件', '提示信息', {
          confirmButtonText: '确定'
        })
      } else {
        let arr = ''
        for (let i = 0; i < selectedNodes.length; i++) {
          if (i === selectedNodes.length - 1) {
            arr +=
              selectedNodes[i].code +
              '_' +
              selectedNodes[i].timestamp
                .replace(/-/g, '')
                .replace(/:/g, '')
                .replace(/ /g, '')
          } else {
            arr +=
              selectedNodes[i].code +
              '_' +
              selectedNodes[i].timestamp
                .replace(/-/g, '')
                .replace(/:/g, '')
                .replace(/ /g, '') +
              ','
          }
        }
        const url = 'http://172.16.90.1:18080/result/download'
        const fileForm = this.$refs.fileForm
        const input = this.$refs.input
        fileForm.setAttribute('action', url)
        input.setAttribute('value', arr)
        fileForm.submit()
      }
    },
    loadVideo(node) {
      debugger
      const vm = this
      app
        .postData('result_video', {
          code: node.code,
          resultId: node.resultId,
          timestamp: node.timestamp
        })
        .then(data => {
          if (data.code === 0) {
            const path = data.data
            vm.$refs.videoPlsyer.showVideo(path, this.ipAddress)
          }
        })
    },
    comChanged(val) {
      this.window12 = val
    },
    signalFilter() {
      const vm = this
      // 从备份的signal中还原
      this.signalList = JSON.parse(JSON.stringify(this.signalListBak))
      // 执行过滤
      this.signalList = this.signalList.filter(function(signal) {
        if (vm.input !== '' && vm.input != null) {
          if (signal.signalName !== vm.input) {
            return false
          }
        }
        return true
      })
    },
    drawChart() {
      const vm = this
      const selected1 = JSON.parse(JSON.stringify(this.headersData))
      const tmp1 = JSON.parse(JSON.stringify(this.sig1Seled))
      const selected2 = JSON.parse(JSON.stringify(this.headersData))
      const tmp2 = JSON.parse(JSON.stringify(this.sig2Seled))

      if (tmp1.length > 0) {
        for (let i = 0; i < selected1.length; i++) {
          selected1[i].variables = tmp1
        }
        app
          .postData('result_datas', {
            resultId: this.checkedFile[0].id,
            selectedVariables: selected1
          })
          .then(data => {
            if (data.code === 0) {
              vm.$refs.chart.clearChart(1)
              vm.$refs.chart.setData(data.data, 1)
            }
          })
      }
      if (tmp2.length > 0) {
        for (let i = 0; i < selected2.length; i++) {
          selected2[i].variables = tmp2
        }
        app
          .postData('result_datas', {
            resultId: this.checkedFile[0].id,
            selectedVariables: selected2
          })
          .then(data => {
            if (data.code === 0) {
              vm.$refs.chart.clearChart(2)
              vm.$refs.chart.setData(data.data, 2)
            }
          })
      }
    },
    changeHeight() {
      this.tableHeight = document.body.offsetHeight - 230 + 'px'
      this.chartHeight = {
        height: document.body.offsetHeight - 100 + 'px'
      }
    },
    handleChange(activeNames) {
      const height = document.body.offsetHeight - (activeNames.length === 0 ? 230 : 450) + 'px'
      this.tableHeight = height
    }
  }
}
</script>

<style>
.result .el-form-item {
  margin-bottom: 0px;
}

.result .el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 100%;
}

.result .el-select {
  display: inline-block;
  position: relative;
  width: 100%;
}

.result .title {
  color: white;
  font-size: 14px;
  background-color: #7eb2db;
  line-height: 36px;
  text-align: center;
}

.result .dateGrp {
  width: 70%;
}

.result .treeDiv {
  overflow-y: scroll;
}

.result .bt {
  margin-left: 2%;
  width: 96%;
}

.result .btgrp {
  display: flex;
  justify-content: space-around;
  position: absolute;
  bottom: 0px;
  width: 100%;
}

.result .grid-content {
  height: calc(100vh - 50px);
  padding: 0px 10px;
  border-right: 1px solid #ebeef5;
  border-top: 1px solid #ebeef5;
  position: relative;
}

.result .chartDiv {
  overflow-y: scroll;
}

#data-result .el-table .cell {
  white-space: pre-line; /*保留换行符*/
}
</style>
