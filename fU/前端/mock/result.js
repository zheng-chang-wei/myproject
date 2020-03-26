import Mock from 'mockjs'

export default [
  {
    url: '/result/all',
    type: 'post',
    response: config => {
      console.log(config.body)
      if(config.body.QueryRequest.pageNum == 1){
        return {
          code: 0,
          total: 500,
          data: [{id: 1, fileType: 1, name: '前端开发规范.docx'},
            {id: 2, fileType: 1, name: '数据文件测试'},
            {id: 3, fileType: 2, name: '视频文件测试1'},
            {id: 4, fileType: 2, name: '视频文件测试2'},
            {id: 5, fileType: 1, name: 'test5'},
            {id: 6, fileType: 1, name: 'test6'},
            {id: 7, fileType: 1, name: 'test7'},
            {id: 8, fileType: 1, name: 'test8'},
            {id: 9, fileType: 1, name: 'test9'},
            {id: 10, fileType: 1, name: 'test10'},
            {id: 11, fileType: 1, name: 'test11'},
            {id: 12, fileType: 1, name: 'test12'},
            {id: 13, fileType: 1, name: 'test13'},
            {id: 14, fileType: 1, name: 'test14'},
            {id: 15, fileType: 1, name: 'test15'},
            {id: 16, fileType: 1, name: 'test16'},
            {id: 17, fileType: 1, name: 'test17'},
            {id: 18, fileType: 1, name: 'test18'},
            {id: 19, fileType: 1, name: 'test19'},
            {id: 20, fileType: 1, name: 'test20'}]
        }
      }else {
        return {
          code: 0,
          total: 500,
          data: [{id: 21, fileType: 1, name: 'test1'},
            {id: 22, fileType: 1, name: '数据文件测试'},
            {id: 23, fileType: 2, name: '视频文件测试1'},
            {id: 24, fileType: 2, name: '视频文件测试2'},
            {id: 25, fileType: 1, name: 'test5'},
            {id: 26, fileType: 1, name: 'test6'},
            {id: 27, fileType: 1, name: 'test7'},
            {id: 28, fileType: 1, name: 'test8'},
            {id: 29, fileType: 1, name: 'test9'},
            {id: 30, fileType: 1, name: 'test10'},
            {id: 31, fileType: 1, name: 'test11'},
            {id: 32, fileType: 1, name: 'test12'},
            {id: 33, fileType: 1, name: 'test13'},
            {id: 34, fileType: 1, name: 'test14'},
            {id: 35, fileType: 1, name: 'test15'},
            {id: 36, fileType: 1, name: 'test16'},
            {id: 37, fileType: 1, name: 'test17'},
            {id: 38, fileType: 1, name: 'test18'},
            {id: 39, fileType: 1, name: 'test19'},
            {id: 40, fileType: 1, name: 'test20'}]
        }
      }
    }
  },
  {
    url: '/result/list',
    type: 'post',
    response: config => {
      console.log(config.body)
      if(config.body.QueryRequest.pageNum == 1){
        return {
          code: 0,
          total: 45,
          data: [{id: 1, fileType: 1, name: '前端开发规范.docx'},
            {id: 2, fileType: 1, name: '数据文件测试'},
            {id: 3, fileType: 2, name: '视频文件测试1'},
            {id: 4, fileType: 2, name: '视频文件测试2'},
            {id: 5, fileType: 1, name: 'test5'},
            {id: 6, fileType: 1, name: 'test6'},
            {id: 7, fileType: 1, name: 'test7'},
            {id: 8, fileType: 1, name: 'test8'}]
        }
      }else{
        return {
          code: 0,
          total: 45,
          data: [{id: 21, fileType: 1, name: 'test1'},
            {id: 22, fileType: 1, name: '数据文件测试'},
            {id: 323, fileType: 2, name: '视频文件测试1'},
            {id: 42, fileType: 2, name: '视频文件测试2'},
            {id: 25, fileType: 1, name: 'test5'},
            {id: 26, fileType: 1, name: 'test6'},
            {id: 27, fileType: 1, name: 'test7'},
            {id: 28, fileType: 1, name: 'test8'}]
        }
      }
    }
  },
  {
    url: '/result/header',
    type: 'post',
    response: config => {
      console.log(config.body.resultId)
      return {
        code: 0,
        data: {
          version: 1,
          headers: [
            {
              type: 'MVB', consumptionId: '1', slotId: '1', multicastIp: '127.0.0.1', multicastPort: '65535',
              variables: [
                {groupId: 1, signalName: '分析信号电压', dataType: 7},
                {groupId: 1, signalName: 'xinhaxinhao2o2', dataType: 7},
                {groupId: 1, signalName: '电流xinhao23', dataType: 7},
                {groupId: 1, signalName: '电xinhao2流4', dataType: 7},
                {groupId: 1, signalName: 'xinhao2电压5', dataType: 7},
                {groupId: 1, signalName: '电流6', dataType: 7},
                {groupId: 1, signalName: 'xinhao2', dataType: 7},
                {groupId: 1, signalName: 'xinhao2电流8', dataType: 7},
                {groupId: 1, signalName: 'xinhao2电流9', dataType: 7},
                {groupId: 1, signalName: 'xinhao2电流10', dataType: 7},
                {groupId: 1, signalName: '电xinhao2压11', dataType: 7},
                {groupId: 1, signalName: '电流xinhao212', dataType: 7}
              ]
            }
          ]
        }
      }
    }
  },
  {
    url: '/result/video',
    type: 'post',
    response: config => {
      console.log(config.body.resultId)
      if(config.body.resultId==1){
        return {
          code: 0,
          data: "http://localhost/data/前端开发规范.docx"
        }
      }
      if(config.body.resultId==2){
        return {
          code: 0,
          data: "http://localhost/data/111.dat"
        }
      }
      if(config.body.resultId==3){
        return {
          code: 0,
          data: "http://localhost/video/111.mp4"
        }
      }
      if(config.body.resultId==4){
        return {
          code: 0,
          data: "http://localhost/video/222.mp4"
        }
      }
    }
  }
]
