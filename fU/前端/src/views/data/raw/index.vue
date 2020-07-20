<template>
  <el-row id="data-raw">
    <el-col :span="7">
      <el-row v-show="fileOrData" class="grid-content">
        <form ref="fileForm" action method="get" style="display:none">
          <input ref="input" type="text" name="filePath" value>
        </form>
        <el-form label-width="80px" style="width: 100%;">
          <div class="title">原始数据文件选择</div>
          <el-form-item label="开始时间" size="small">
            <el-date-picker v-model="startDate" class="dateGrp" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="开始时间" />
          </el-form-item>
          <el-form-item label="结束时间" size="small">
            <el-date-picker v-model="endDate" class="dateGrp" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="结束时间" />
          </el-form-item>
        </el-form>
        <el-button type="primary" style="width: 100%" size="mini" @click="getFileList">搜索</el-button>
        <div class="treechartDiv" :style="treeHeight">
          <el-tree
            ref="fileTree"
            :props="props"
            :data="rootNode"
            node-key="name"
            :load="loadNode"
            lazy
            show-checkbox
          />
        </div>
        <div class="btgrp" style="margin-bottom:5px">
          <el-button size="mini" type="primary" @click="downloadRawFiles">下载</el-button>
          <el-button size="mini" type="success" @click="getCheckedNodes">查看</el-button>
          <el-button size="mini" type="danger" @click="delRawFiles">删除</el-button>
        </div>
      </el-row>
      <el-row v-show="!fileOrData" class="grid-content">
        <switch-button @comChanged="comChanged($event,'switchBtn')" />
        <el-table
          v-show="window12==1"
          ref="multiSig1"
          :data="groupSignalList"
          row-key="myGroup"
          :height="tableHeight"
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
          :row-style="{height:'30px',fontSize: '13px'}"
          :cell-style="{padding:'0px'}"
          @selection-change="countAndPush1"
        >
          <el-table-column type="selection" :selectable="whitchIsSelectable" width="45" />
          <el-table-column prop="ComId" label="ComId" width="90" />
          <el-table-column prop="signalName" label="信号名" width="90" />
          <el-table-column prop="name" label="变量名" width="90" />
          <el-table-column prop="type" label="变量类型" width="90" />
        </el-table>
        <el-table
          v-show="window12==2"
          ref="multiSig2"
          :data="groupSignalList"
          row-key="myGroup"
          :height="tableHeight"
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
          :row-style="{height:'30px',fontSize: '13px'}"
          :cell-style="{padding:'0px'}"
          @selection-change="countAndPush2"
        >
          <el-table-column type="selection" :selectable="whitchIsSelectable" width="45" />
          <el-table-column prop="ComId" label="ComId" width="90" />
          <el-table-column prop="signalName" label="信号名" width="90" />
          <el-table-column prop="name" label="变量名" width="90" />
          <el-table-column prop="type" label="变量类型" width="90" />
        </el-table>
        <div class="btgrp">
          <el-button type="primary" style="width: 40%" size="mini" @click="fileOrData = true">返回</el-button>
          <el-button type="success" style="width: 40%" size="mini" @click="drawChart">绘图</el-button>
        </div>
      </el-row>
    </el-col>
    <el-col :span="17">
      <div class="treechartDiv" :style="chartHeight">
        <chart ref="chart" />
      </div>
      <next-frame ref="nextFrame" @comChanged="comChanged($event,'nextFrame')" />
    </el-col>
  </el-row>
</template>

<script>
import app from '@/common/js/app'
import SwitchButton from '@/components/Base/SwitchButton'
import NextFrame from '@/components/Base/NextFrame'
import chart from '../chart'

export default {
  name: 'Index',
  components: { SwitchButton, chart, NextFrame },
  data() {
    return {
      treeHeight: {},
      chartHeight: {},
      tableHeight: 0,
      startDate: '',
      endDate: '',
      fileOrData: true,
      rootNode: [],
      modeFlag: 'all',
      LeafNodes: [],
      headersData: [],
      window12: '1',
      signalList: [],
      groupSignalList: [],
      sigSelLimit: 5,
      sig1Seled: [],
      sig2Seled: [],
      offset: 1,
      props: {
        label: 'displayname',
        children: 'file',
        isLeaf: 'isLeaf'
      }
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  mounted() {
    this.getAllFileList()
  },
  methods: {
    // root all
    getAllFileList() {
      const vm = this
      this.modeFlag = 'all'
      app.postData('data_root').then(data => {
        if (data.code === 0) {
          vm.rootNode = data.data
          for (let i = 0; i < vm.rootNode.length; i++) {
            vm.rootNode[i].displayname = vm.formatMonth(
              vm.rootNode[i].name
            )
          }
        }
      })
    },
    // root list
    getFileList() {
      const vm = this
      this.modeFlag = 'list'
      if (
        this.startDate === '' ||
        this.startDate === null ||
        this.endDate === '' ||
        this.endDate === null
      ) {
        this.$alert('必须选择开始结束时间', '提示信息', {
          confirmButtonText: '确定'
        })
        return
      }
      if (this.startDate > this.endDate) {
        this.$alert('结束时间必须大于等于开始时间', '提示信息', {
          confirmButtonText: '确定'
        })
        return
      }
      app
        .postData('data_query', {
          startDate: this.startDate,
          endDate: this.endDate
        })
        .then(data => {
          if (data.code === 0) {
            vm.rootNode = data.data
            for (let i = 0; i < vm.rootNode.length; i++) {
              if (vm.rootNode[i].level === 'Month') {
                vm.rootNode[i].isLeaf = false
                vm.rootNode[i].displayname = this.formatMonth(vm.rootNode[i].name)
              }
            }
          }
        })
    },
    // 二级以下的节点 懒加载
    loadNode(node, resolve) {
      if (node.data.length !== undefined) {
        return
      } // TODO 排除第一次加载
      if (this.modeFlag === 'all') {
        app
          .postData('data_list', {
            FileTreeNode: {
              path: node.data.path,
              name: node.data.name,
              level: node.data.level
            }
          })
          .then(data => {
            if (data.code === 0) {
              const tmpData = data.data
              for (let i = 0; i < tmpData.length; i++) {
                if (tmpData[i].level === 'Leaf') {
                  tmpData[i].isLeaf = true
                  tmpData[i].displayname = this.formatSecond(tmpData[i].name)
                } else {
                  tmpData[i].isLeaf = false
                  tmpData[i].displayname = this.formatDay(tmpData[i].name)
                }
              }
              resolve(tmpData)
            }
          })
      } else {
        app
          .postData('data_query', {
            FileTreeNode: {
              path: node.data.path,
              name: node.data.name,
              level: node.data.level
            },
            startDate: this.startDate,
            endDate: this.endDate
          })
          .then(data => {
            if (data.code === 0) {
              const tmpData = data.data
              for (let i = 0; i < tmpData.length; i++) {
                if (tmpData[i].level === 'Leaf') {
                  tmpData[i].isLeaf = true
                  tmpData[i].displayname = this.formatSecond(tmpData[i].name)
                } else {
                  tmpData[i].isLeaf = false
                  tmpData[i].displayname = this.formatDay(tmpData[i].name)
                }
              }
              resolve(tmpData)
            }
          })
      }
    },
    getCheckedNodes() {
      this.LeafNodes = []
      const FileTreeNodes = this.$refs.fileTree.getCheckedNodes()
      for (let i = 0; i < FileTreeNodes.length; i++) {
        if (FileTreeNodes[i].level === 'Leaf') {
          this.LeafNodes.push(FileTreeNodes[i])
        }
      }
      if (this.LeafNodes.length >= 1) {
        this.loadHeader(this.LeafNodes)
      } else {
        this.$alert('没有选择数据文件', '提示信息', {
          confirmButtonText: '确定'
        })
      }
    },
    delRawFiles() {
      const vm = this
      const filePath = this.$refs.fileTree.getCheckedNodes()
      app.get('state').then(res => {
        if (res.code === 0) {
          if (res.data === 'RUNNING') {
            vm.$message({
              type: 'error',
              message: '运行状态下不能删除数据'
            })
          } else {
            app.postData('data_delete', { filePath: filePath }).then(data => {
              this.$message({
                duration: 1500,
                showClose: true,
                type: data.code === 0 ? 'success' : 'error',
                message: data.data
              })
              if (data.code === 0) {
                for (let i = 0; i < filePath.length; i++) {
                  vm.$refs.fileTree.remove(filePath[i])
                }
              }
            })
          }
        }
      })
    },
    downloadRawFiles() {
      const FileTreeNodes = this.$refs.fileTree.getCheckedNodes()
      let arr = ''
      for (let i = 0; i < FileTreeNodes.length; i++) {
        if (
          FileTreeNodes[i].level === 'Month' ||
          FileTreeNodes[i].level === 'Date'
        ) {
          arr = FileTreeNodes[i].path
          break
        } else if (i === FileTreeNodes.length - 1) {
          arr += FileTreeNodes[i].path
        } else {
          arr += FileTreeNodes[i].path + ','
        }
      }
      const url = 'http://172.16.90.1:18080/data/download'
      const fileForm = this.$refs.fileForm
      const input = this.$refs.input
      fileForm.setAttribute('action', url)
      input.setAttribute('value', arr)
      fileForm.submit()
    },
    loadHeader(LeafNodes) {
      const vm = this
      app
        .postData('data_header', {
          FileTreeNodes: LeafNodes
        })
        .then(data => {
          vm.fileOrData = false
          if (data.code === 0) {
            vm.signalList = []
            if (data.data != null) {
              vm.headersData = JSON.parse(JSON.stringify(data.data.headers))
              for (let i = 0; i < vm.headersData.length; i++) {
                for (let j = 0; j < vm.headersData[i].variables.length; j++) {
                  vm.headersData[i].variables[j].myGroup =
                  vm.headersData[i].variables[j].comId +
                  vm.headersData[i].variables[j].sourceIp
                  vm.headersData[i].variables[j].type = vm.headersData[i].type
                  vm.signalList.push(vm.headersData[i].variables[j])
                }
              }
            }

            // 信号分组
            const groupList = vm.groupBy(vm.signalList, link => {
              return link.myGroup
            })
            vm.groupSignalList = []
            for (var key in groupList) {
              const map = {
                myGroup: key,
                ComId: groupList[key][0].comId,
                children: groupList[key]
              }
              vm.groupSignalList.push(map)
            }
            vm.fileOrData = false
          }
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
    whitchIsSelectable(row, index) {
      if (row.type === undefined) {
        return false
      } else {
        return true
      }
    },
    comChanged(val, flag) {
      if (flag === 'switchBtn') {
        this.window12 = val
      } else if (flag === 'nextFrame') {
        this.offset = val
        this.drawChart()
      }
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
          .postData('data_datas', {
            FileTreeNodes: this.LeafNodes,
            offset: this.offset,
            selectedVariables: selected1
          })
          .then(data => {
            if (data.code === 0) {
              vm.$refs.chart.clearChart(1)
              vm.$refs.chart.setData(data.data, 1)
            }
          })
          .catch(message => {
            vm.$refs.nextFrame.reduceIndex()
          })
      }
      if (tmp2.length > 0) {
        for (let i = 0; i < selected2.length; i++) {
          selected2[i].variables = tmp2
        }
        app
          .postData('data_datas', {
            FileTreeNodes: this.LeafNodes,
            offset: this.offset,
            selectedVariables: selected2
          })
          .then(data => {
            if (data.code === 0) {
              vm.$refs.chart.clearChart(2)
              vm.$refs.chart.setData(data.data, 2)
            }
          })
          .catch(message => {})
      }
    },
    groupBy(list, fn) {
      const groups = {}
      list.forEach(function(o) {
        const group = JSON.stringify(fn(o))
        groups[group] = groups[group] || []
        groups[group].push(o)
      })
      return groups
    },
    formatMonth(originalname) {
      var displayname = originalname
      if (originalname.length === 6) {
        displayname = originalname.substr(0, 4) + '年'
        displayname = displayname + originalname.substr(4, 2) + '月'
      }
      return displayname
    },
    formatDay(originalname) {
      var displayname
      if (originalname.length === 2) {
        displayname = originalname + '日'
      }
      return displayname
    },
    formatSecond(originalname) {
      var displayname = originalname
      if (originalname.length === 6) {
        displayname =
          originalname.substr(0, 2) +
          ':' +
          originalname.substr(2, 2) +
          ':' +
          originalname.substr(4, 2)
      }
      return displayname
    },
    changeHeight() {
      this.treeHeight = {
        height: document.body.offsetHeight - 300 + 'px'
      }
      this.chartHeight = {
        height: document.body.offsetHeight - 100 + 'px'
      }
      this.tableHeight = document.body.offsetHeight - 150 + 'px'
    }

  }
}
</script>

<style>
#data-raw .el-form-item {
  margin-bottom: 0px;
}

#data-raw .el-date-editor.el-input,
#data-raw .el-date-editor.el-input__inner {
  width: 100%;
}

#data-raw .el-select {
  display: inline-block;
  position: relative;
  width: 100%;
}

#data-raw .title {
  color: white;
  font-size: 14px;
  background-color: #7eb2db;
  height: 36px;
  line-height: 36px;
  text-align: center;
}

#data-raw .treechartDiv {
  overflow-y: scroll;
}

#data-raw .btgrp {
  display: flex;
  justify-content: space-around;
  position: absolute;
  bottom: 0px;
  width: 100%;
}

#data-raw .grid-content {
  height: calc(100vh - 50px);
  padding: 0px 10px;
  border-right: 1px solid #ebeef5;
  border-top: 1px solid #ebeef5;
  position: relative;
}
</style>
