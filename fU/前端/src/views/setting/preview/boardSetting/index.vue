<template>
  <el-card class="box-card" shadow="hover">
    <el-row slot="header" class="center">
      <el-col :span="23"><span>板卡配置</span></el-col>
      <el-col :span="1">
        <el-button type="text" @click="editClicked">编辑</el-button>
      </el-col>
    </el-row>
    <el-collapse style="margin:5px 10px">
      <template v-for="(board,index) in mvbBoardSettings">
        <el-collapse-item :key="index" :title="board.type.replace('CARD_TYPE_','')">
          <el-form label-position="left" label-width="100px">
            <el-form-item label="使能：">
              <el-switch v-model="board.enable" disabled />
            </el-form-item>
            <el-form-item label="卡槽号">
              {{ board.slotId }}
            </el-form-item>
            <el-form-item label="前面板IP">
              {{ board.ip }}
            </el-form-item>
            <el-form-item label="数据流文件">
              {{ board.fileOriginalName }}
            </el-form-item>
          </el-form>
        </el-collapse-item>
      </template>
      <template v-for="(board,index) in adBoardSettings">
        <el-collapse-item :key="index+mvbBoardSettings.length" :title="board.type.replace('CARD_TYPE_','')">
          <el-form label-position="left" label-width="100px">
            <el-form-item label="使能：">
              <el-switch v-model="board.enable" disabled />
            </el-form-item>
            <el-form-item label="卡槽号：">
              {{ board.slotId }}
            </el-form-item>
            <el-form-item label="前面板IP：">
              {{ board.ip }}
            </el-form-item>
          </el-form>
          <el-table
            :data="board.variables"
            style="margin-top:20px; width: 100%"
          >
            <el-table-column prop="chnId" label="通道号" />
            <el-table-column prop="sampleRate" label="采样率" />
            <el-table-column prop="name" label="变量名" />
          </el-table>
        </el-collapse-item>
      </template>
      <template v-for="(board,index) in otherBoardSettings">
        <el-collapse-item :key="index+mvbBoardSettings.length+otherBoardSettings.length" :title="board.type.replace('CARD_TYPE_','')">
          <el-form label-position="left" label-width="100px">
            <el-form-item label="使能：">
              <el-switch v-model="board.enable" disabled />
            </el-form-item>
            <el-form-item label="卡槽号">
              {{ board.slotId }}
            </el-form-item>
            <el-form-item label="前面板IP">
              {{ board.ip }}
            </el-form-item>
          </el-form>
        </el-collapse-item>
      </template>
    </el-collapse>
  </el-card>
</template>

<script>
export default {
  components: {

  },
  props: {
    boardSettings: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
    }
  },
  computed: {
    mvbBoardSettings() {
      return this.boardSettings.filter(function(board, index) {
        return board.type.indexOf('MVB') !== -1 || board.type.indexOf('ECN') !== -1
      })
    },
    adBoardSettings() {
      return this.boardSettings.filter(function(board, index) {
        return board.type.indexOf('AD') !== -1
      })
    },
    otherBoardSettings() {
      return this.boardSettings.filter(function(board, index) {
        return board.type.indexOf('AD') === -1 && board.type.indexOf('MVB') === -1 && board.type.indexOf('ECN') === -1
      })
    }
  },
  methods: {
    editClicked() {
      this.$emit('edit', 1)
    }
  }
}
</script>

<style>

</style>
