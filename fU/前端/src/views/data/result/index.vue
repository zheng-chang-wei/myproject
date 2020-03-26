<template>
  <div style="height: 100%;">
    <table style="width: 100%;height: 100%;table-layout: fixed;">
      <tr>
        <td style="width: 25%;">
          <div class="grid-content">
            <div class="title"><span>信号分析数据文件选择</span><span class="left-triangle" @click="toggleFileSel"></span></div>
            <div class="selGrp">
              开始日期：
              <el-date-picker class="dateGrp" v-model="startDate" type="date" placeholder="选择日期"></el-date-picker>
            </div>
            <div class="selGrp">
              结束日期：
              <el-date-picker class="dateGrp" v-model="endDate" align="right" type="date"
                              placeholder="选择日期"></el-date-picker>
            </div>
            <button class="bt el-button el-button--primary" @click="getFileList(1)">搜索</button>
            <div class="treeDiv">
              <el-table ref="mytable"
                        :row-key="getRowKeys"
                        :data="fileList"
                        @selection-change="handleSelectionChange"
                        border>
                <el-table-column
                  type="selection"
                  :reserve-selection="ifReserve"
                  width="40">
                </el-table-column>
                <el-table-column
                  prop="id"
                  label="id"
                  width="50">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="文件名">
                </el-table-column>
              </el-table>
            </div>
            <div class="btgrp" style="bottom: 40px;">
              <el-pagination
                background
                small
                layout="prev, pager, next"
                :page-size="pageSize"
                :total="total"
                :current-page="pageNum"
                @current-change="changPageNum">
              </el-pagination>
            </div>
            <div class="btgrp" style="bottom: 0px;">
              <el-button type="primary" @click="fileDownLoad">导出</el-button>
              <el-button type="success" @click="getCheckedNodes()">解析</el-button>
              <el-button type="danger" @click="delHeaders">删除</el-button>
            </div>
          </div>
        </td>
        <transition name="el-zoom-in-center">
          <td style="width: 25%;" v-show="sigSelShow&ifChart">
            <div class="grid-content">
              <div class="title">
                <span>原始数据选择</span><span class="left-triangle" @click="toggleSigSel(0)"></span></div>
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
        <td style="vertical-align: top;">
          <div v-show="ifChart" class="grid-content">
            <div style="line-height: 50px;padding-left: 50px;">
              <button class="el-button el-button--success">绘图</button>
            </div>
            <line-chart></line-chart>
          </div>
          <div v-show="!ifChart">
            <video-play ref="videoPlsyer" :uri="videoUri"/>
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
  import app from '@/common/js/app'
  import lineChart from "./lineChart";
  import videoPlay from "./videoPlay";

  export default {
    name: "index",
    components: {lineChart, videoPlay},
    data() {
      return {
        startDate: '',
        endDate: '',
        fileList: [],
        modeFlag: 'all',
        checkedFile: [],
        ifReserve: true,
        pageSize: 20,
        pageNum: 1,
        total: 20,
        sigSelShow: false,
        headersData: [],
        input: '',
        signalList: [],
        signalLimit: 2,
        signalCount: 0,
        ipAddress: '',
        videoUri: 'http://localhost/video/111.mp4',
        ifChart: true
      }
    },
    mounted() {
      this.getAllFileList(1)
    },
    methods: {
      // 分页 第一页 all
      getAllFileList: function (ifFirst) {
        if (ifFirst == 1) {
          this.pageNum = 1
          this.modeFlag = 'all'
        }
        let _this = this
        app.postData('result_all', {
          QueryRequest: {
            pageSize: this.pageSize,
            pageNum: this.pageNum
          }
        }).then(data => {
          if (data.code == 0) {
            _this.total = data.total
            _this.fileList = data.data
          }
        })
      },
      // 分页 第一页 list
      getFileList: function (ifFirst) {
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
        if (ifFirst == 1) {
          this.pageNum = 1
          this.modeFlag = 'list'
        }
        let _this = this
        app.postData('result_list', {
          QueryRequest: {
            pageSize: this.pageSize,
            pageNum: this.pageNum
          },
          ResultQueryParam: {
            start: this.startDate,
            end: this.endDate
          }
        }).then(data => {
          if (data.code == 0) {
            _this.total = data.total
            _this.fileList = data.data
          }
        })
      },
      changPageNum: function (cpage) {
        this.pageNum = cpage
        if (this.modeFlag == 'all') {
          this.getAllFileList()
        } else {
          this.getFileList()
        }
      },
      handleSelectionChange: function (files) {
        this.checkedFile = files
      },
      getRowKeys: function (row) {
        return row.id;
      },
      fileDownLoad: function () {
        if (this.checkedFile.length >= 2) {
          this.$alert('针对分析数据解析结果只支持单选模式', '提示信息', {
            confirmButtonText: '确定'
          });
        } else if (this.checkedFile.length == 1) {
          this.loadFile(this.checkedFile[0].id)
        } else {
          this.$alert('没有选择数据文件', '提示信息', {
            confirmButtonText: '确定',
          });
        }
      },
      getCheckedNodes() {
        if (this.checkedFile.length >= 2) {
          this.$alert('针对分析数据解析结果只支持单选模式', '提示信息', {
            confirmButtonText: '确定'
          });
        } else if (this.checkedFile.length == 1) {
          if (this.checkedFile[0].fileType == 1) { //TODO 数据文件
            this.ifChart = true
            this.loadHeader(this.checkedFile[0].id)
          } else {//TODO 视频文件
            this.ifChart = false
            this.loadVideo(this.checkedFile[0].id)
          }
        } else {
          this.$alert('没有选择数据文件', '提示信息', {
            confirmButtonText: '确定',
          });
        }
      },
      delHeaders() {
        this.signalList = []
        this.$refs.mytable.clearSelection()
        this.$refs.videoPlsyer.showVideo('', '')
      },
      loadHeader(resultId) {
        let _this = this
        app.postData('result_header', {
          resultId: '678987'
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
          }
        )
        _this.toggleSigSel(1)
      },
      loadFile: function (resultId) {
        let _this = this
        app.postData('result_video',
          {resultId: resultId})
          .then(data => {
            if (data.code == 0) {
              let path = data.data
              try {
                let arr = path.split('/')
                fetch(path).then(res => res.blob()).then(blob => {
                  const a = document.createElement('a');
                  document.body.appendChild(a)
                  a.style.display = 'none'
                  const url = window.URL.createObjectURL(blob);
                  a.href = url;
                  a.download = arr[arr.length - 1]; //获取文件名
                  a.click();
                  document.body.removeChild(a)
                  window.URL.revokeObjectURL(url);
                });
              } catch (e) {
                alert("下载异常！");
              }
            }
          })
      },
      loadVideo: function (resultId) {
        let _this = this
        app.postData('result_video', {
          resultId: resultId
        }).then(data => {
            if (data.code == 0) {
              let path = data.data
              _this.$refs.videoPlsyer.showVideo(path, this.ipAddress)
            }
          }
        )
      },
      countAndPush: function (obj) {
        obj.target.checked ? this.signalCount++ : this.signalCount--;
        if (this.signalCount > this.signalLimit) {
          obj.target.checked = false;
          this.$alert('最多只能选择两个信号', '提示信息', {
            confirmButtonText: '确定',
          });
          this.signalCount--;
        }
      },
      toggleSigSel: function (flag) {
        if (flag == 1) {
          this.sigSelShow = true
        } else {
          this.sigSelShow = false
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
    max-height: calc(100vh - 280px);
  }

  .bt {
    margin-left: 2%;
    width: 96%;
  }

  .btgrp {
    display: flex;
    justify-content: space-around;
    width: 100%;
    position: absolute;
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
