<template>
  <section id="sheet-list">
    <van-collapse v-model="activeGroup">
      <van-collapse-item class="collapse-cell" title="待处理工单" name="edit">
        <van-cell v-for="(sheet,index) in sheets" :key="index" class="sheet-cell" @click="editSheet(sheet)">
          <template slot="title">
            <span class="custom-title">{{ sheet.doorId }} — {{ sheet.faultName }}</span>
          </template>
          <template slot="label">
            <span>{{ sheet.project }}号线{{ sheet.trainId }}车 — {{ sheet.faultTime }}</span>
          </template>
          <template>
            <van-tag type="primary" size="medium">{{ sheet.state }}</van-tag>
          </template>
        </van-cell>
      </van-collapse-item>
    </van-collapse>
    <van-cell-group title="历史工单">
      <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
        <van-cell v-for="(sheet,index) in editedSheets" :key="index" class="sheet-cell" @click="showSheet(sheet)">
          <template slot="title">
            <span class="custom-title">{{ sheet.doorId }} — {{ sheet.faultMode }}</span>
          </template>
          <template slot="label">
            <span>{{ sheet.project }}号线{{ sheet.trainId }}车 — {{ sheet.faultTime }}</span>
          </template>
          <template>
            <van-tag type="success" size="medium">{{ sheet.state }}</van-tag>
          </template>
        </van-cell>
      </van-list>
    </van-cell-group>
    <van-goods-action>
      <van-goods-action-button type="primary" text="新建工单" @click="addSheet" />
    </van-goods-action>
    <van-popup v-model="showAdd" position="bottom" style="height: 100%" closeable @close="closeEdit">
      <van-cell title="创建工单" title-style="font-weight:bold;font-size:24px" />
      <worksheet
        :basic-info-form="workdetail"
        :show="showAdd"
        :sheet-id="sheetId"
        :pic-urls="picUrls"
        @commit="commit"
        @addPicture="addPicture"
        @deletePicture="deletePicture"
      />
    </van-popup>
    <van-popup v-model="showInfo" position="bottom" style="height: 100%" closeable>
      <sheetInfo :basic-info-form="sheetInfo" :pic-urls="picUrls" :close-time="closeTime" />
      <van-button size="large" type="primary" @click="showInfo=false">返回</van-button>
    </van-popup>
    <van-popup v-model="showEdit" position="bottom" style="height: 100%" closeable @close="closeEdit">
      <editSheet
        :basic-info-form="workdetail"
        :show="showEdit"
        :suggestion="suggestion"
        :sheet-id="sheetId"
        :pic-urls="picUrls"
        @commit="commit"
        @addPicture="addPicture"
        @deletePicture="deletePicture"
      />
    </van-popup>
  </section>
</template>
<script>
import worksheet from '@/components/worksheet/worksheet'
import sheetInfo from '@/components/worksheet/sheetInfo'
import editSheet from '@/components/worksheet/editSheet'
import app from '@/common/js/app'
import utils from '@/common/js/util'
export default {
  components: {
    worksheet,
    sheetInfo,
    editSheet
  },
  data() {
    return {
      sheets: [],
      editedSheets: [],
      showAdd: false,
      showEdit: false,
      workdetail: {
        count: 0,
        processorCount: 0,
        partCount: 0
      },
      showInfo: false,
      sheetInfo: {},
      activeGroup: ['edit'],
      loading: false,
      finished: false,
      query: {
        pageSize: 10,
        pageNum: 1
      },
      closeTime: '',
      sheetId: -1,
      picUrls: [],
      picNames: [],
      commited: false,
      suggestion: {}
    }
  },
  mounted() {
    const sid = utils.getUrlKey('sheetId')
    if (sid) {
      this.editSheet({
        id: parseInt(sid)
      })
    }
    this.getSheets()
  },
  methods: {
    getSheets() {
      this.getEditSheets()
      this.editedSheets = []
      this.query.pageNum = 1
      this.getHistorySheets()
    },
    getEditSheets() {
      this.sheets = []
      app.get('editSheets').then(res => {
        var data = res.data
        if (data) {
          data.forEach(record => {
            this.sheets.push(record.sheet)
          })
        }
      })
    },
    getHistorySheets() {
      var vm = this
      app.get('historySheets', this.query).then(res => {
        var data = res.data
        if (data) {
          vm.loading = false
          if (data.length < vm.query.pageSize) {
            vm.finished = true
          }
          data.forEach(record => {
            vm.editedSheets.push(record.sheet)
          })
        }
      })
    },
    commit(sheetId, detail) {
      const vm = this
      var url = ''
      var params = {}
      detail.imagePath = this.picNames.join(',')
      if (sheetId === -1) {
        url = 'create_work_detail'
        params = detail
      } else {
        url = 'commit_work_detail'
        params = {
          sheetId: sheetId,
          object: detail
        }
      }
      app.postData(url, params).then(res => {
        vm.showAdd = false
        this.editedSheets = []
        this.query.pageNum = 1
        this.getHistorySheets()
        vm.commited = true
      }).catch(() => {
        vm.$toast({
          message: '提交失败\n请联系管理员',
          type: 'fail'
        })
      })
    },
    addPicture(file, detail) {
      console.log(file)
      console.log(detail)
      var vm = this
      const formData = new FormData()
      formData.append('file', file.file)
      app.uploadFile('upload_file', formData).then(data => {
        if (data.code === 0) {
          vm.picUrls.push(this.GLOBAL.serverIpAndPort + data.data)
          vm.picNames.push(data.data)
          vm.$toast('上传成功')
        }
      }).catch(() => {
        vm.$toast('上传失败')
      })
    },
    deletePicture(pics) {
      const vm = this
      const del = []
      pics.forEach(pic => {
        del.push(vm.picNames[pic.detail.index])
      })
      vm.picNames = vm.picNames.filter(item => {
        return del.indexOf(item) < 0
      })
      const param = {
        filepaths: del.join(',')
      }
      app.post('delete_file_byFilepaths', param).then(data => {
        if (data) {
          vm.$toast('删除成功')
        }
      })
    },
    addSheet() {
      this.workdetail = {
        count: 0,
        processorCount: 0,
        partCount: 0
      }
      this.commited = false
      this.sheetId = -1
      this.picUrls = []
      this.showAdd = true
    },
    editSheet(sheet) {
      this.sheetId = sheet.id
      this.picUrls = []
      this.commited = false
      app.get('worksheet', {
        sheetId: sheet.id
      }).then(res => {
        var data = res.data
        if (data) {
          this.workdetail = data.detail
          this.suggestion = data.suggestion
          this.showEdit = true
        }
      })
    },
    showSheet(sheet) {
      const vm = this
      this.closeTime = ''
      app.get('worksheet', {
        sheetId: sheet.id
      }).then(res => {
        var data = res.data
        if (data) {
          vm.sheetInfo = data.detail
          if (data.sheet.state === '关闭') {
            vm.closeTime = data.steps[data.steps.length - 1].endTime
          }
          vm.picUrls = []
          if (vm.sheetInfo.imagePath) {
            vm.picNames = vm.sheetInfo.imagePath.split(',')
            vm.picNames.forEach(pic => {
              vm.picUrls.push(this.GLOBAL.serverIpAndPort + pic)
            })
          }
          vm.showInfo = true
        }
      })
    },
    onLoad() {
      setTimeout(() => {
        this.query.pageNum += 1
        this.getHistorySheets()
      }, 500)
    },
    closeEdit() {
      const vm = this
      if (!this.commited) {
        const param = {
          filepaths: vm.picNames.join(',')
        }
        vm.picNames = []
        app.post('delete_file_byFilepaths', param).then(data => {
          console.log('delete pics')
        })
      }
    }
  }
}

</script>
<style>
  .van-goods-action-button {
    border-radius: 0px;
    margin-right: 0px;
    margin-left: 0px;
  }

  .van-button {
    margin-top: 0px;
  }

  .van-collapse-item__content {
    padding: 0px;
  }

  .collapse-cell .van-cell {
    background-color: #f8f8f8;
  }

  .collapse-cell .sheet-cell {
    background-color: #ffffff;
  }

</style>
