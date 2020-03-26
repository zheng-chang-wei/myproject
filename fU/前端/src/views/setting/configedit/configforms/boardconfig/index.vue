<template>
  <div>
    <el-button type="primary" style="margin-bottom:10px; margin-top:10px" @click="dialogFormVisible = true">添加板卡</el-button>

    <div style="min-height: 200px; margin-bottom: 50px">
      <template v-for="(item,index) in boardSettings">
        <mvb v-if="item.type==='MVB'||item.type==='ECN'" :key="index" :mvb-form="item" :index="index" @deleteBoard="deleteBoard" />
        <ad v-else-if="item.type==='AD1'||item.type==='AD2'||item.type==='AD3'" :key="index" :ad-form="item" :index="index" @deleteBoard="deleteBoard" />
        <other v-else :key="index" :other-form="item" :index="index" @deleteBoard="deleteBoard" />
      </template>
    </div>
    <div style="position: absolute; bottom: 10px ">
      <el-button style="width: 210px" @click="cancel">取消</el-button>
      <el-button style="width: 210px" type="primary" @click="previousStep">上一步</el-button>
      <el-button style="width: 210px" type="primary" @click="toAlgorithmConfig">下一步</el-button>
    </div>
    <el-dialog title="选择板卡种类" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item label="板卡名称">
          <el-select v-model="boardType" placeholder="请选择板卡">
            <el-option
              v-for="item in cards"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible=false">取 消</el-button>
        <el-button type="primary" @click="confirm()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import mvb from './mvb'
import ad from './ad'
import other from './other'
export default {
  components: {
    mvb,
    ad,
    other
  },
  props: {
    active: {
      type: Number,
      default: null
    },
    boardSettings: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
      dialogFormVisible: false,
      boardType: '',
      cards: ['CPU', 'PHM_IMX8', 'PHM_TX2', 'PHM_AGX', 'PHM_MVB',
        'ECN', 'MVB', 'AD1', 'AD2', 'AD3', 'WIRELESS', 'SSD', 'POWER']
    }
  },
  mounted() {
  },
  methods: {
    previousStep() {
      this.$emit('previousStep')
    },
    toAlgorithmConfig() {
      this.$emit('toAlgorithmConfig', this.boardSettings)
    },
    cancel() {
      this.$emit('cancel')
    },
    confirm() {
      this.boardSettings.push({
        type: this.boardType,
        enable: false,
        variables: []
      })
      this.dialogFormVisible = false
    },
    deleteBoard(index) {
      this.boardSettings.splice(index, 1)
    }
  }
}
</script>

<style>

</style>
