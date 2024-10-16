import Teacher from './teacher.js'


class ReqRequest{
    data(){
        return {
            id:number,
            teacher:Teacher,
            status:"",
            created:Date,
            changed:Date
        }
    }
}

export default new RegRequest()