<template>
  <!-- 添加对象和特征 -->
  <el-row id="addObj">
    <el-row class="objTable" :style="'height:'+commonHeight/2+'px'">
      <el-row class="center" style="height:25px">
        {{ type }}对象
      </el-row>
      <el-button style="width:97%;margin:0px 5px" icon="el-icon-plus" type="primary" size="mini" @click="openAddObjDialog">添加</el-button>
      <el-table ref="table" :data="selectedObjTableDatas" style="width: 100%;" :height="(commonHeight/2)-55" border :row-style="{ fontSize: '11px'}" :header-cell-style="{padding:'3px',fontSize:'12px'}" :cell-style="{padding:'3px'}">
        <el-table-column prop="comId" label="comId" align="center" width="63" />
        <el-table-column prop="ip" label="ip" align="center" />
        <el-table-column v-if="type==='CsPort'" prop="port" label="port" align="center" width="55" />
        <el-table-column align="center" label="操作" width="56">
          <template slot-scope="scope">
            <el-button
              type="danger"
              icon="el-icon-delete"
              @click.stop="handleDelete(scope.$index, scope.row)"
            />
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <comIdDialog v-if="type==='ComId'" ref="comIdDialog" :selected-obj-table-datas="selectedObjTableDatas" @addComIdObjConfirm="addComIdObjConfirm" />
    <csPortDialog v-if="type==='CsPort'" ref="csPortDialog" @addComIdObjConfirm="addComIdObjConfirm" />
  </el-row>
</template>

<script>

import comIdDialog from './components/comIdDialog/index'
import csPortDialog from './components/csPortDialog/index'
export default {
  components: {
    comIdDialog,
    csPortDialog
  },
  props: {
    type: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      commonHeight: 0,
      selectedObjTableDatas: []
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeHeight)
  },
  mounted() {
  },
  methods: {
    changeHeight() {
      this.commonHeight = document.body.offsetHeight - 62
    },
    openAddObjDialog() {
      switch (this.type) {
        case 'ComId':
          this.$refs.comIdDialog.openAddObjDialog()
          break
        case 'CsPort':
          this.$refs.csPortDialog.openAddObjDialog()
          break

        default:
          break
      }
    },
    handleDelete(index, row) {
      this.selectedObjTableDatas.splice(index, 1)
      this.$bus.$emit('objChange', this.selectedObjTableDatas)
    },
    addComIdObjConfirm(selection) {
      this.selectedObjTableDatas = selection
      this.$bus.$emit('objChange', this.selectedObjTableDatas)
    }

  }
}
</script>

<style>
#addObj .cell .el-button{
  padding: 9px 8px;
  font-size: 10px;
  border-radius: 15px;
}
.objTable{
    margin-left:5px;
    margin-top: 5px;
    border-radius: 2px;
    border: 1px solid #CCCCCC;
    line-height: 1px;
}
#addObj .el-dialog__body {
    padding: 5px 15px;
    color: #606266;
    font-size: 14px;
    word-break: break-all;
}
#addObj .el-form-item__label {
    padding: 0 5px 0 0;

}
#addObj .el-input__inner{
  padding: 0 5px;
}
</style>
