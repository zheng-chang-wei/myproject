<template>
  <div style="height: 100%;">
    <table style="width: 100%;height: 100%;table-layout: fixed;">
      <tr>
        <td style="width: 25%;">
          <div class="grid-content">
            <div class="title"><span>原始数据文件选择</span><span class="left-triangle" @click="toggleFileSel"></span></div>
            <div class="selGrp">
              开始日期：
              <el-date-picker class="dateGrp" v-model="startDate" type="date" placeholder="选择日期"></el-date-picker>
            </div>
            <div class="selGrp">
              结束日期：
              <el-date-picker class="dateGrp" v-model="endDate" align="right" type="date"
                              placeholder="选择日期"></el-date-picker>
            </div>
            <button class="bt el-button el-button--primary" @click="getFileList">搜索</button>
            <div class="treeDiv">
              <el-tree ref="fileTree" :props="props" :data="rootNode" node-key="name" :load="loadNode" lazy
                       show-checkbox></el-tree>
            </div>
            <div class="btgrp">
              <el-button type="primary">导出</el-button>
              <el-button type="success" @click="getCheckedNodes">解析</el-button>
              <el-button type="danger" @click="delHeaders">删除</el-button>
            </div>
          </div>
        </td>
        <transition name="el-zoom-in-center">
          <td style="width: 25%;" v-show="sigSelShow">
            <div class="grid-content">
              <div class="title">
                <span>原始数据选择</span><span class="left-triangle" @click="toggleSigSel(0)"></span></div>
              <div class="selGrp">
                数据源：
                <el-select v-model="v1" clearable placeholder="请选择">
                  <el-option v-for="(item,i) in headersData" :key="item.value" :label="item.type"
                             :value="item.type"></el-option>
                </el-select>
              </div>
              <div class="selGrp">
                ComId：
                <el-select style="width:20%;" v-model="v2" clearable placeholder="请选择">
                  <el-option v-for="(item,i) in headersData" :key="i" :label="item.slotId"
                             :value="item.slotId"></el-option>
                </el-select>
                srcIp：
                <el-select style="width:38%;" v-model="v3" clearable placeholder="请选择">
                  <el-option v-for="(item,i) in headersData" :key="i" :label="item.multicastIp"
                             :value="item.multicastIp"></el-option>
                </el-select>
              </div>
              <div class="selGrp">
                信号名：
                <el-input style="width: 70%" v-model="input" placeholder="请输入内容"></el-input>
              </div>
              <button class="bt el-button el-button--primary">搜索</button>
              <div class="treeDiv" style="line-height: 23px;padding:10px 0px 0px 40px;">
                <label style="font-weight:400;" v-for="(varible,i) in signalList">
                  <input type="checkbox" @click="countAndPush"/>
                  <span style="padding-left: 10px"> {{varible.signalName}}</span>
                  <span style="padding-left: 10px">{{varible.dataType}}</span> <br/></label>
              </div>
            </div>
          </td>
        </transition>
        <td>
          <div id="lineChart" class="grid-content">
            <div style="line-height: 50px;padding-left: 50px;">
              <button class="el-button el-button--success">绘图</button>
            </div>
            <line-chart></line-chart>
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
  import app from '@/common/js/app'
  import lineChart from "./lineChart";

  export default {
    name: "index",
    components: {lineChart},
    data() {
      return {
        startDate: '',
        endDate: '',
        fileSelShow: true,
        sigSelShow: false,
        rootNode: [],
        modeFlag: 'all',
        v1: '',
        v2: '',
        v3: '',
        v4: '',
        input: '',
        headersData: [],
        signalList: [],
        signalLimit: 2,
        signalCount: 0,
        props: {
          label: 'name',
          children: 'file',
          isLeaf: 'isLeaf'
        },
        chartSpan: 18
      }
    },
    mounted() {
      this.getAllFileList()
    },
    methods: {
      // root all
      getAllFileList: function () {
        let _this = this
        this.modeFlag = 'all'
        app.postData('data_root').then(data => {
          if (data.code == 0) {
            _this.rootNode = data.data
          }
        })
      },
      // root list
      getFileList: function () {
        let _this = this
        this.modeFlag = 'list'
        if (this.startDate == '' || this.endDate == '') {
          this.$alert('必须选择开始结束日期', '提示信息', {
            confirmButtonText: '确定'
          });
          return;
        }
        if (this.startDate > this.endDate) {
          this.$alert('结束日期必须大于等于开始日期', '提示信息', {
            confirmButtonText: '确定',
          });
          return;
        }
        app.postData('data_query', {
          FileTreeNode: null,
          startDate: this.startDate,
          endDate: this.endDate
        }).then(data => {
          if (data.code == 0) {
            _this.rootNode = data.data
          }
        })
      },
      // 二级以下的节点 懒加载
      loadNode(node, resolve) {
        if (node.data.length != undefined) {
          return
        } //TODO 排除第一次加载
        if (this.modeFlag == 'all') {
          app.postData('data_list', {
            FileTreeNode: {
              path: node.data.path,
              name: node.data.name,
              level: node.data.level
            }
          }).then(data => {
            if (data.code == 0) {
              let tmpData = data.data
              for (let i = 0; i < tmpData.length; i++) {
                if (tmpData[i].level == 'Leaf') {
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
            if (data.code == 0) {
              let tmpData = data.data
              for (let i = 0; i < tmpData.length; i++) {
                if (tmpData[i].level == 'Leaf') {
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
        let FileTreeNodes = this.$refs.fileTree.getCheckedNodes()
        if (FileTreeNodes.length >= 1) {
          this.loadHeader(FileTreeNodes)
        } else {
          this.$alert('没有选择数据文件', '提示信息', {
            confirmButtonText: '确定'
          });
        }
      },
      delHeaders() {
        this.headersData = []
        this.signalList = []
        this.$refs.fileTree.setCheckedKeys([]);
      },
      loadHeader(FileTreeNodes) {
        let _this = this
        app.postData('data_header', {
          FileTreeNodes: FileTreeNodes
        }).then(data => {
            if (data.code == 0) {
              _this.signalList = []
              _this.headersData = JSON.parse(JSON.stringify(data.data.headers))
              for (let i = 0; i < _this.headersData.length; i++) {
                for (let j = 0; j < _this.headersData[i].variables.length; j++) {
                  _this.signalList.push(_this.headersData[i].variables[j])
                }
              }
            }
            _this.toggleSigSel(1)
          }
        )
      },
      countAndPush: function (obj) {
        obj.target.checked ? this.signalCount++ : this.signalCount--;
        if (this.signalCount > this.signalLimit) {
          obj.target.checked = false;
          alert("over");
          this.signalCount--;
        }
      },
      toggleSigSel: function (flag) {
        if (flag == 1) {
          this.sigSelShow = true
          this.chartSpan = 12
        } else {
          this.sigSelShow = false
          this.chartSpan = 18
        }
      },
      toggleFileSel: function () {
        this.fileSelShow = !this.sigSelShow
      }
    }
  }
</script>

<style scoped>
  .title {
    color: white;
    background-color: #7eb2db;
    height: 30px;
    line-height: 30px;
    padding-left: 5px;
  }

  .dateGrp {
    width: 70%;
  }

  .selGrp {
    line-height: 40px;
    display: flex;
    justify-content: start;
    padding-left: 10px;
  }

  .treeDiv {
    overflow-y: scroll;
    max-height: calc(100vh - 230px);
  }

  .bt {
    margin-left: 2%;
    width: 96%;
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
    border-right: 1px solid #EBEEF5;
    border-top: 1px solid #EBEEF5;
    position: relative;
    font-size: 14px;
    font-weight: 400 !important;
  }

  .left-triangle {
    width: 0px;
    height: 0px;
    border-right: 10px solid white;
    border-top: 5px solid transparent;
    border-bottom: 5px solid transparent;
    margin: 7px;
    z-index: 50;
    display: inline-block;
    float: right;
  }
</style>
