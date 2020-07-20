<template>
  <el-row>
    <el-col :span="6">
      <el-row v-show="fileOrData" class="grid-content">
        <el-form label-width="80px" style="width: 100%;">
          <div class="title">原始数据文件选择</div>
          <el-form-item label="开始日期">
            <el-date-picker v-model="startDate" class="dateGrp" type="date" placeholder="开始日期" />
          </el-form-item>
          <el-form-item label="结束日期">
            <el-date-picker
              v-model="endDate"
              class="dateGrp"
              type="date"
              placeholder="结束日期"
            />
          </el-form-item>
        </el-form>
        <el-button type="primary" style="width: 100%" size="mini" @click="getFileList">搜索</el-button>
        <div class="treeDiv">
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
        <div class="btgrp">
          <el-button type="primary">下载</el-button>
          <el-button type="success" @click="getCheckedNodes">查看</el-button>
          <el-button type="danger" @click="delHeaders">删除</el-button>
        </div>
      </el-row>
      <el-row v-show="!fileOrData" class="grid-content">
        <switch-button @comChanged="comChanged($event,'switchBtn')" />
        <el-form label-width="80px">
          <el-form-item label="srcIp">
            <el-select v-model="v2" clearable>
              <el-option
                v-for="(ip,i) in sourceIpList"
                :key="i"
                :label="ip"
                :value="ip"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="ComId">
            <el-select v-model="v3" clearable>
              <el-option
                v-for="(comid,i) in comIdList"
                :key="i"
                :label="comid"
                :value="comid"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <el-button type="primary" style="width: 100%" @click="signalFilter">搜索</el-button>
        <el-table
          v-show="window12==1"
          ref="multiSig1"
          :data="signalList"
          height="300"
          style="width: 100%"
          @selection-change="countAndPush1"
        >
          <el-table-column
            type="selection"
            width="55"
          />
          <el-table-column
            label="信号名"
            prop="signalName"
          />
          <el-table-column
            label="sourceIp"
            prop="sourceIp"
          />
        </el-table>
        <el-table
          v-show="window12==2"
          ref="multiSig2"
          :data="signalList"
          height="300"
          style="width: 100%"
          @selection-change="countAndPush2"
        >
          <el-table-column
            type="selection"
            width="55"
          />
          <el-table-column
            label="信号名"
            prop="signalName"
          />
          <el-table-column
            label="sourceIp"
            prop="sourceIp"
          />
        </el-table>
        <div class="btgrp">
          <el-button
            type="primary"
            style="width: 40%"
            size="mini"
            @click="fileOrData = true"
          >返回</el-button>
          <el-button
            type="success"
            style="width: 40%"
            size="mini"
            @click="drawChart"
          >绘图</el-button>
        </div>
      </el-row>
    </el-col>
    <el-col :span="18">
      <div style="height: 500px;overflow-y: scroll;">
        <chart ref="chart" />
      </div>
      <next-frame
        ref="nextFrame"
        @comChanged="comChanged($event,'nextFrame')"
      />
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
      startDate: '',
      endDate: '',
      fileOrData: true,
      rootNode: [],
      modeFlag: 'all',
      LeafNodes: [],
      v2: '',
      v3: '',
      headersData: [],
      sourceIpList: null,
      comIdList: null,
      window12: '1',
      signalList: [],
      signalListBak: [],
      sigSelLimit: 5,
      sig1Seled: [],
      sig2Seled: [],
      offset: 1,
      props: {
        label: 'name',
        children: 'file',
        isLeaf: 'isLeaf'
      }
    }
  },
  mounted() {
    this.getAllFileList()
  },
  methods: {
    // root all
    getAllFileList: function() {
      const vm = this
      this.modeFlag = 'all'
      app.postData('data_root').then(data => {
        if (data.code === 0) {
          vm.rootNode = data.data
        }
      })
    },
    // root list
    getFileList: function() {
      debugger
      const vm = this
      this.modeFlag = 'list'
      if (this.startDate === '' || this.startDate == null || this.endDate === '' || this.endDate == null) {
        this.$alert('必须选择开始结束日期', '提示信息', {
          confirmButtonText: '确定'
        })
        return
      }
      if (this.startDate > this.endDate) {
        this.$alert('结束日期必须大于等于开始日期', '提示信息', {
          confirmButtonText: '确定'
        })
        return
      }
      app.postData('data_query', {
        FileTreeNode: null,
        startDate: this.startDate,
        endDate: this.endDate
      }).then(data => {
        if (data.code === 0) {
          vm.rootNode = data.data
        }
      })
    },
    // 二级以下的节点 懒加载
    loadNode(node, resolve) {
      if (node.data.length !== undefined) {
        return
      } // TODO 排除第一次加载
      if (this.modeFlag === 'all') {
        app.postData('data_list', {
          FileTreeNode: {
            path: node.data.path,
            name: node.data.name,
            level: node.data.level
          }
        }).then(data => {
          if (data.code === 0) {
            const tmpData = data.data
            for (let i = 0; i < tmpData.length; i++) {
              if (tmpData[i].level === 'Leaf') {
                tmpData[i].disabled = false
                tmpData[i].isLeaf = true
              } else {
                tmpData[i].disabled = true
                tmpData[i].isLeaf = false
              }
            }
            resolve(tmpData)
          }
        })
      } else {
        app.postData('data_query', {
          FileTreeNode: {
            path: node.data.path,
            name: node.data.name,
            level: node.data.level
          },
          startDate: this.startDate,
          endDate: this.endDate
        }).then(data => {
          if (data.code === 0) {
            const tmpData = data.data
            for (let i = 0; i < tmpData.length; i++) {
              if (tmpData[i].level === 'Leaf') {
                tmpData[i].disabled = false
                tmpData[i].isLeaf = true
              } else {
                tmpData[i].disabled = true
                tmpData[i].isLeaf = false
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
    delHeaders() {
      this.headersData = []
      this.signalList = []
      this.$refs.fileTree.setCheckedKeys([])
    },
    loadHeader(LeafNodes) {
      const vm = this
      app.postData('data_header', {
        FileTreeNodes: LeafNodes
      }).then(data => {
        vm.fileOrData = false
        if (data.code === 0) {
          vm.signalList = []
          const set1 = new Set()
          const set2 = new Set()
          vm.headersData = JSON.parse(JSON.stringify(data.data.headers))
          for (let i = 0; i < vm.headersData.length; i++) {
            for (let j = 0; j < vm.headersData[i].variables.length; j++) {
              vm.signalList.push(vm.headersData[i].variables[j])
              set1.add(vm.headersData[i].variables[j].sourceIp)
              set2.add(vm.headersData[i].variables[j].comId)
            }
          }
          console.log(vm.signalList)
          vm.signalListBak = JSON.parse(JSON.stringify(vm.signalList))
          vm.fileOrData = false
          vm.sourceIpList = set1
          vm.comIdList = set2
        }
      }
      )
    },
    countAndPush1: function(val) {
      if (val.length > this.sigSelLimit) {
        this.$refs.multiSig1.toggleRowSelection(val[val.length - 1], false)
        this.$alert('同一窗口最多添加5个信号', '提示信息', {
          confirmButtonText: '确定'
        })
        return false
      } else if (val.length > 0) {
        const signalName = val[val.length - 1].signalName
        const nowcomId = val[val.length - 1].comId
        const nowsourceIp = val[val.length - 1].sourceIp
        for (let i = 0; i < this.sig2Seled.length; i++) {
          if (signalName === this.sig2Seled[i].signalName &&
              nowcomId === this.sig2Seled[i].comId &&
              nowsourceIp === this.sig2Seled[i].sourceIp) {
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
    countAndPush2: function(val) {
      if (val.length > this.sigSelLimit) {
        this.$refs.multiSig2.toggleRowSelection(val[val.length - 1], false)
        this.$alert('同一窗口最多添加5个信号', '提示信息', {
          confirmButtonText: '确定'
        })
        return false
      } else if (val.length > 0) {
        debugger
        const signalName = val[val.length - 1].signalName
        const nowcomId = val[val.length - 1].comId
        const nowsourceIp = val[val.length - 1].sourceIp
        for (let i = 0; i < this.sig1Seled.length; i++) {
          if (signalName === this.sig1Seled[i].signalName &&
              nowcomId === this.sig1Seled[i].comId &&
              nowsourceIp === this.sig1Seled[i].sourceIp) {
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
    signalFilter: function() {
      const vm = this
      // 从备份的signal中还原
      this.signalList = JSON.parse(JSON.stringify(this.signalListBak))
      // 执行过滤
      this.signalList = this.signalList.filter(function(signal) {
        // const flag = true
        if (vm.v2 !== '' && vm.v2 != null) {
          if (signal.sourceIp !== vm.v2) {
            return false
          }
        }
        if (vm.v3 !== '' && vm.v3 != null) {
          if (signal.comId !== vm.v3) {
            return false
          }
        }
        return true
      })
    },
    comChanged: function(val, flag) {
      if (flag === 'switchBtn') {
        this.window12 = val
      } else if (flag === 'nextFrame') {
        this.offset = val
        this.drawChart()
      }
    },
    drawChart: function() {
      const vm = this
      const selected1 = JSON.parse(JSON.stringify(this.headersData))
      const tmp1 = JSON.parse(JSON.stringify(this.sig1Seled))
      const selected2 = JSON.parse(JSON.stringify(this.headersData))
      const tmp2 = JSON.parse(JSON.stringify(this.sig2Seled))

      if (tmp1.length > 0) {
        for (let i = 0; i < selected1.length; i++) {
          selected1[i].variables = tmp1
        }
        app.postData('data_datas', {
          FileTreeNodes: this.LeafNodes,
          offset: this.offset,
          selectedVariables: selected1
        }).then(data => {
          if (data.code === 0) {
            vm.$refs.chart.clearChart(1)
            vm.$refs.chart.setData(data.data, 1)
          }
        }).catch(message => {
          vm.$refs.nextFrame.reduceIndex()
        })
      }
      if (tmp2.length > 0) {
        for (let i = 0; i < selected2.length; i++) {
          selected2[i].variables = tmp2
        }
        app.postData('data_datas', {
          FileTreeNodes: this.LeafNodes,
          offset: this.offset,
          selectedVariables: selected2
        }).then(data => {
          if (data.code === 0) {
            vm.$refs.chart.clearChart(2)
            vm.$refs.chart.setData(data.data, 2)
          }
        }).catch(message => {
        })
      }
    }
  }
}
</script>

<style scoped>
  .el-form-item {
    margin-bottom: 0px;
  }

  .el-date-editor.el-input, .el-date-editor.el-input__inner {
    width: 100%;
  }

  .el-select {
    display: inline-block;
    position: relative;
    width: 100%;
  }

  .title {
    color: white;
    font-size: 14px;
    background-color: #7eb2db;
    height: 36px;
    line-height: 36px;
    text-align: center;
  }

  .treeDiv {
    overflow-y: scroll;
    max-height: calc(100vh - 230px);
  }

  .btgrp {
    display: flex;
    justify-content: space-around;
    position: absolute;
    bottom: 0px;
    width: 100%;
  }

  .grid-content {
    height: calc(100vh - 50px);
    padding: 0px 10px;
    border-right: 1px solid #EBEEF5;
    border-top: 1px solid #EBEEF5;
    position: relative;
  }

</style>
