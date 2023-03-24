package com.study.http;

import com.alibaba.fastjson.JSON;
import com.study.http.dto.ClinicDto;
import com.study.http.dto.ClinicEmrDto;
import com.study.http.dto.ClinicEmrPatientInfo;
import com.study.http.dto.ClinicEmrResultInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockDataUtil {
    public static List<String> getInpatientColumnTwo(){
        List<String> columnList = new ArrayList<>();
        columnList.add("dv87654334566");  //record_id
        columnList.add("入院记录");  //record_type
        columnList.add("medical_id2");  //medical_id
        columnList.add("gd876543456");  //visit_id
        columnList.add("76543567");  //patient_info->id
        columnList.add("35岁");  //patient_info->age
        columnList.add("岁");  //patient_info->unit
        columnList.add("男");  //patient_info->gender
        columnList.add("2021-5-12");  //patient_info->birth_date
        columnList.add("否");  //patient_info->marital_status
        columnList.add("4");  //patient_info->admission_count
        columnList.add("2022-5-12 14:12:11");  //record_time
        columnList.add("2022-5-12 14:12:11");  //admission_time
        columnList.add("盛京医院");  //hospital
        columnList.add("皮肤外科");  //dept
        columnList.add("姓名:啊\n" +
                "年龄:43岁\n" +
                "性别:男 \n" +
                "住院号: \n" +
                "民族:汉族 \n" +
                "出生地:广东\n" +
                "现住址:\n" +
                "籍贯: 广东 \n" +
                "病史叙述者：父亲 \n" +
                "与患者关系:父女\n" +
                "联系电话：\n" +
                "入院日期:2019年9月26日10时  \n" +
                "记录时间:2019年9月26日17时 \n" +
                "父亲姓名:\n" +
                "年龄:34岁\n" +
                "联系电话:\n" +
                "文化程度: 初中\n" +
                "职业:司机\n" +
                "母亲姓名:\n" +
                "年龄：33岁\n" +
                "联系电话:\n" +
                "文化程度: 小学\n" +
                "职业:卖猪肉 \n" +
                "主诉：皮肤有伤\n" +
                //"现病史：手臂有伤。\n" +
                //"个人史：一切正常。\n" +
                //"既往史：健康状况良好。 \n" +
                "过敏史：海鲜\n" +
                "输血史：一次\n" +
                "预防接种史：麻风\n" +
                "婚育史：未婚\n" +
                "月经史：无\n" +
                "家族史：无遗产\n" +
                "专科检查：手臂有异\n" +
                "体  格  检  查\n" +
                "全身部位正常\n" +
                "  专科检查：神志清楚，反应良好。\n" +
                "辅助检查：1.拟右上肺尖、后段炎症。2.两下肺透亮度不均匀增高，不除外小气道病变，请结合临床。3.气管支气管重建未见异常。4.左侧筛窦及上颌窦少量炎症。\n" +
                "初步诊断：*1.过敏 \n" +
                "*2.皮肤敏感 \n" +
                "*3.中毒 \n" +
                "住院医师签名： \n" +
                "日期 : 2022年2月22日                    \n" +
                "修正诊断：*1.过敏\n" +
                "*2.皮肤敏感\n" +
                "*3.中毒                   \n" +
                "上级医师签名：\n" +
                "日期 : 2022年2月22日\n" +
                "最后诊断：1.过敏\n" +
                "2.皮肤敏感\n" +
                "3.中毒\n" +
                "上级医师签名：\n" +
                "日期 : 2022年2月22日 ");  //text

        return columnList;
    }

    public static List<String> getInpatientColumn(){
        List<String> columnList = new ArrayList<>();
        columnList.add("sd2313213123213");  //record_id
        columnList.add("入院记录");  //record_type
        columnList.add("medical_id");  //medical_id
        columnList.add("sd2313213123213");  //visit_id
        columnList.add("2313123213");  //patient_info->id
        columnList.add("45岁");  //patient_info->age
        columnList.add("岁");  //patient_info->unit
        columnList.add("女");  //patient_info->gender
        columnList.add("2008-11-1");  //patient_info->birth_date
        columnList.add("是");  //patient_info->marital_status
        columnList.add("2");  //patient_info->admission_count
        columnList.add("2021-10-23 11:11:11");  //record_time
        columnList.add("2021-10-22 12:13:23");  //admission_time
        columnList.add("广医附一");  //hospital
        columnList.add("心血管内科");  //dept
        columnList.add("姓名:\n" +
                "年龄:6岁\n" +
                "性别:女 \n" +
                "住院号: \n" +
                "民族:汉族 \n" +
                "出生地:广东\n" +
                "现住址:\n" +
                "籍贯: 广东 \n" +
                "病史叙述者：父亲 \n" +
                "与患者关系:父女\n" +
                "联系电话：\n" +
                "入院日期:2019年9月26日10时  \n" +
                "记录时间:2019年9月26日17时 \n" +
                "父亲姓名:\n" +
                "年龄:34岁\n" +
                "联系电话:\n" +
                "文化程度: 初中\n" +
                "职业:司机\n" +
                "母亲姓名:\n" +
                "年龄：33岁\n" +
                "联系电话:\n" +
                "文化程度: 小学\n" +
                "职业:卖猪肉 \n" +
                "主诉：反复咳喘4年余，再发5天\n" +
                "现病史：患儿4年前余（2岁）开始反复出现咳嗽，多为干咳，感染诱发，与天气变化、接触粉尘、运动、进食酸甜食物及体位变化无明显关系，伴鼻痒、流涕，间有喘息，每年喘息2-3次（门诊病历提示可闻及喘鸣音），在当地医院就诊，予雾化抗感染后可缓解，每次病程7天至14天，间歇期1-2个月，期间间有清咽样咳嗽，少许鼻痒、流涕，喜揉鼻子、眼睛，时有鼻衄，可自行缓解。1年余前在当地医院诊断支气管哮喘，予雾化吸入糖皮质激素及支气管舒张剂（雾化机约500元），雷诺考特喷鼻，间断口服顺尔宁，期间仍后反复咳嗽，偶有喘息，性质同前。3月前吸入信必可都保80μg/4.5μg 1吸 BIDx1月，期间无咳嗽，后自行停药。半年余前因“1、支气管炎 2、支气管哮喘 3、鼻炎 4、轻度中枢性睡眠呼吸暂停低通气综合征 5、上气道咳嗽综合征”在我科住院（住院时间2019-04-17至2019-04-21），出院后雾化吸入普米克令舒1ml+博利康尼1ml+生理盐水1ml BIDx2周，随后调整为雾化吸入普米克令舒1ml+生理盐水2ml至今，期间剧烈运动后有喘息，几乎每周均出现，按需吸入万托林后可缓解。5天前再次出现咳嗽，呈阵发性连声咳，痰多，可咳出白色粘稠样痰，无明显喘息，无发热，在当地医院予口服头孢孟泊肟酯（09-20至09-22）、口服阿奇霉素1天（09-23）、静脉滴注头孢孟多酯2天（09-24至09-25）、甲强龙2天（09-24至09-25）、多索茶碱（09-24至09-25）联合雾化吸入普米克令舒1mg+博利康尼5mg BID后仍有频密咳嗽，痰多，现为进一步诊疗收入我院。患儿平素有鼻痒、鼻塞，否认异物吸入史，否认午后潮热、夜间盗汗等不适。近期精神、睡眠、胃纳可，大小便未见明显异常，近期体重无明显下降。\n" +
                "个人史：原籍出生长大，无外地居住史，无疫区居住史，无疫水、疫源接触史。无吸烟史，无嗜酒史，无放射性物质、毒物接触史。\n" +
                "既往史：平素健康状况一般，患儿足月顺产娩出，出生体重3.2kg，否认窒息抢救史。 \n" +
                "过敏史：牛奶、鱼、小麦、花生、黄豆\n" +
                "输血史：无\n" +
                "预防接种史：乙肝\n" +
                "婚育史：已婚\n" +
                "月经史：正常\n" +
                "家族史：家族中无相关疾病记载，无传染病及遗传病等病史。\n" +
                "专科检查：正常\n" +
                "体  格  检  查\n" +
                "T36.7℃ P95次/分 R24次/分 BP93/71mmHg 身高120cm 体重24.1kg\n" +
                "  一般情况：发育良好，营养良好，神志清醒，自主体位，正常面容，查体合作\n" +
                "  皮肤及黏膜：皮肤弹性良好，皮肤及粘膜未见黄染，未见淤点及淤斑，无皮下结节或肿块。无蜘蛛痣及肝掌。\n" +
                "   淋巴结：浅表淋巴结未及肿大，无压痛。\n" +
                " 头部及其器官：头颅无畸形，眼睑正常，结膜正常，巩膜无黄膜，角膜正常，双色瞳孔等大正圆，对光反射灵敏。耳廓无畸形，外耳道通畅，无异常分泌物，乳突无压痛。鼻无畸形，双侧鼻腔可见少许透明分泌物附着，双侧下鼻甲肿大，堵塞1/2，粘膜充血。，副鼻窦区无压痛。口唇红润，牙龈无红肿，粘膜无溃疡，伸舌居中，扁桃体Ⅰ度肿大，咽稍充血。悬雍垂\n" +
                "  颈部：颈两侧对称，无颈静脉怒张及颈动脉异常搏动，肝颈静脉回流征阴性。颈软无抵抗，气管居中。\n" +
                "  胸部：胸廓对称，两侧正常，呼吸动度两侧一致，肋间隙无增宽或变窄。\n" +
                "  肺脏：两肺叩清音，双肺呼吸音粗，可闻及少许痰鸣音，未闻及哮鸣音。\n" +
                "  心脏：心率95次/分，心音有力，律齐，未闻及病理性杂音。\n" +
                "  腹部：腹部外形正常，无胃型，无肠型及无蠕动波，腹软，无压痛，无反跳痛，无腹部包块，肝脏触诊未触及，胆囊未触及，脾未触及肿大，腹部叩诊呈鼓音，肝区无叩击痛，听诊肠鸣音正常。\n" +
                "  肛门：未见异常。\n" +
                "  外阴：未见异常。\n" +
                "  外阴：未见异常。\n" +
                "  脊柱：脊柱无畸形，无侧弯，各椎体无压痛及叩击痛，活动无障碍。\n" +
                "  四肢：四肢无畸形，活动自如，无杵状指（趾），各关节无红肿，活动无障碍。无双下肢水肿，未见静脉曲张，肌肉无萎缩，肌力正常。\n" +
                "  神经系统：两侧肱二、三头肌反射，膝腱反射及跟腱反射存在，两侧对称，无增强或减弱。双侧Babinski征，Kernig征及Hoffmann征未引出。\n" +
                "  专科检查：神志清楚，反应可。浅表淋巴结未及肿大。双眼睑无浮肿，结膜无充血。双侧鼻腔可见少许透明分泌物附着，双侧下鼻甲肿大，堵塞1/2，粘膜充血。口唇无干裂，口腔粘膜光滑，未见溃疡。咽稍充血，咽后壁可见滤泡增生，双侧扁桃体Ⅰ度肿大，未见脓点。双侧胸廓对称，双肺呼吸音粗，可闻及少许痰鸣音，未闻及哮鸣音。心率95次/分，心音有力，律齐，未闻及病理性杂音。腹软，肝脾肋下未及，肠鸣音正常。四肢末梢暖，肢端无硬肿脱皮，毛细血管充盈时间＜2s\n" +
                "辅助检查：04-19血清过敏原示鸡蛋白、牛奶、鱼、小麦、花生、黄豆(fx5):0.03   0，屋尘、户尘螨、粉尘螨、蟑螂(hx2):0.03   0，点青霉/分枝孢霉/烟曲霉/白假丝酵母/交链孢霉/蠕孢霉(mx2):0.04  0，总IgE(TIgE):26.0。04-22【[胸部气管支气管,三维重建] [副鼻窦,平扫]】：1.拟右上肺尖、后段炎症。2.两下肺透亮度不均匀增高，不除外小气道病变，请结合临床。3.气管支气管重建未见异常。4.左侧筛窦及上颌窦少量炎症。          \n" +
                "初步诊断：*1.急性支气管炎 \n" +
                "*2.支气管哮喘 \n" +
                "*3.鼻-鼻窦炎  \n" +
                "住院医师签名： \n" +
                "日期 : 2019年9月26日                    \n" +
                "修正诊断：*1.急性支气管炎\n" +
                "*2.支气管哮喘\n" +
                "*3.鼻-鼻窦炎                   \n" +
                "上级医师签名：\n" +
                "日期 : 2019年9月27日                                                                                         \n" +
                "最后诊断：1.急性支气管炎\n" +
                "2.支气管哮喘\n" +
                "3.鼻-鼻窦炎\n" +
                "上级医师签名：\n" +
                "日期 : 2019年9月28日 ");  //text

        return columnList;
    }

    public static List<String> getDbColumnTwo(){
        List<String> columnList = new ArrayList<>();
        columnList.add("dadad"); //record_id
        columnList.add("检验记录"); //record_type
        columnList.add("fafdaf"); //medical_id
        columnList.add("fadsf"); //visit_id
        columnList.add("fadfd"); //patient_info>id
        columnList.add("1岁"); //patient_info>age
        columnList.add("年龄单位"); //patient_info>unit
        columnList.add("5"); //patient_info>gender
        columnList.add("2012-11-1"); //patient_info>birth_date
        columnList.add("盛京"); //hospital
        columnList.add("2022-10-23 11:11:11"); //record_time
        columnList.add("皮肤外科"); //dept
        columnList.add("皮肤外科"); //apply_dept
        columnList.add("皮肤"); //exam_name
        columnList.add("皮肤"); //exam_method
        columnList.add("皮肤"); //sample_category
        columnList.add("合格"); //sample_status
        columnList.add("dsad"); //sample_id
        columnList.add("2021-10-23 11:05:09"); //sample_time
        columnList.add("2021-10-23 11:10:09"); //receive_time
        columnList.add("2021-10-23 11:12:09"); //exam_time
        columnList.add("2021-10-23 11:15:09"); //report_time
        columnList.add("皮肤"); //result_info->item_name
        columnList.add("Hb"); //result_info->item_abbr
        columnList.add("153"); //result_info->item_result
        columnList.add("g/L"); //result_info->item_unit
        columnList.add("高"); //result_info->item_hint
        return columnList;
    }

    public static List<String> getDbColumn(){
        List<String> columnList = new ArrayList<>();
        columnList.add("sd2313213123213"); //record_id
        columnList.add("检验记录"); //record_type
        columnList.add("da324141"); //medical_id
        columnList.add("ZY1232132131"); //visit_id
        columnList.add("2313123213"); //patient_info>id
        columnList.add("13岁"); //patient_info>age
        columnList.add("年龄单位"); //patient_info>unit
        columnList.add("1"); //patient_info>gender
        columnList.add("2008-11-1"); //patient_info>birth_date
        columnList.add("广医附一"); //hospital
        columnList.add("2021-10-23 11:11:11"); //record_time
        columnList.add("心血管内科"); //dept
        columnList.add("心血管内科"); //apply_dept
        columnList.add("血常规"); //exam_name
        columnList.add("血液标本"); //exam_method
        columnList.add("血液"); //sample_category
        columnList.add("合格"); //sample_status
        columnList.add("23213"); //sample_id
        columnList.add("2021-10-23 11:05:09"); //sample_time
        columnList.add("2021-10-23 11:10:09"); //receive_time
        columnList.add("2021-10-23 11:12:09"); //exam_time
        columnList.add("2021-10-23 11:15:09"); //report_time
        /*columnList.add("血红蛋白"); //result_info->item_name
        columnList.add("Hb"); //result_info->item_abbr
        columnList.add("153"); //result_info->item_result
        columnList.add("g/L"); //result_info->item_unit
        columnList.add("高"); //result_info->item_hint*/
        columnList.add("血红蛋白;白蛋白"); //result_info->item_name
        columnList.add("Hb;albumin"); //result_info->item_abbr
        columnList.add("153;70"); //result_info->item_result
        columnList.add("g/L;g/dl"); //result_info->item_unit
        columnList.add("高;高"); //result_info->item_hint
        return columnList;
    }

    public static List<String> getElement(){
        List<String> columnList = new ArrayList<>();
        columnList.add("action");  //action
        columnList.add("type");  //type
        columnList.add("param_visit_id");  //param_visit_id
        columnList.add("param_patient_id");  //param_patient_id
        columnList.add("record_id"); //record_id
        columnList.add("record_type"); //record_type
        columnList.add("medical_id"); //record_id
        columnList.add("visit_id"); //record_type
        columnList.add("patient_info_id"); //patient_info>id
        columnList.add("patient_info_age"); //patient_info>age
        columnList.add("patient_info_gender"); //patient_info>gender
        columnList.add("patient_info_birth_date"); //patient_info>birth_date
        columnList.add("record_time"); //record_time
        columnList.add("admission_time"); //admission_time
        columnList.add("hospital"); //hospital
        columnList.add("dept"); //dept
        columnList.add("apply_dept"); //apply_dept
        columnList.add("exam_name"); //exam_name
        columnList.add("exam_method"); //exam_method
        columnList.add("sample_category"); //sample_category
        columnList.add("sample_status"); //sample_status
        columnList.add("sample_id"); //sample_id
        columnList.add("sample_time"); //sample_time
        columnList.add("receive_time"); //receive_time
        columnList.add("exam_time"); //exam_time
        columnList.add("report_time"); //report_time
        columnList.add("item_name"); //item_name
        columnList.add("item_abbr"); //item_abbr
        columnList.add("item_result"); //item_abbr
        columnList.add("item_unit"); //item_abbr
        columnList.add("item_hint"); //item_abbr
        return columnList;
    }

    public static List<String> getColumnThree(){
        List<String> columnList = new ArrayList<>();
        columnList.add("save_emr");  //action
        columnList.add("auxiliary_exam_recommend;clinical_pathway_recommend");  //type
        columnList.add("23");  //param_visit_id
        columnList.add("123456789");  //param_patient_id
        columnList.add("sd2313213123213&qa2345698"); //record_id
        columnList.add("检验记录&住院记录"); //record_type
        columnList.add("da324141&sd12234"); //medical_id
        columnList.add("ZY1232132131&QA234567"); //visit_id
        columnList.add("2313123213&9576534"); //patient_info>id
        columnList.add("13岁&30岁"); //patient_info>age
        columnList.add("1&9"); //patient_info>gender
        columnList.add("2008-11-1&2012-12-10"); //patient_info>birth_date
        columnList.add("2021-10-23 11:11:11&2022-12-11 12:12:12"); //record_time
        columnList.add("2021-10-23 08-23-25&2023-10-23 09-23-25"); //admission_time
        columnList.add("广医附一&盛京附一"); //hospital
        columnList.add("心血管内科&皮肤外科"); //dept
        columnList.add("心血管内科&皮肤外科"); //apply_dept
        columnList.add("血常规&皮肤病"); //exam_name
        columnList.add("血液标本&组织和标本"); //exam_method
        columnList.add("血液&血液"); //sample_category
        columnList.add("合格&合格"); //sample_status
        columnList.add("23213&97988"); //sample_id
        columnList.add("2021-10-23 11:05:09&2023-10-23 11:05:09"); //sample_time
        columnList.add("2021-10-23 11:10:09&2023-10-23 11:10:09"); //receive_time
        columnList.add("2021-10-23 11:12:09&2023-10-23 11:12:09"); //exam_time
        columnList.add("2021-10-23 11:15:09&2023-10-23 11:15:09"); //report_time
        columnList.add("血红蛋白;白蛋白&血清;血脂"); //result_info->item_name
        columnList.add("Hb;albumin&KD;dada"); //result_info->item_abbr
        columnList.add("153;70&173;85"); //result_info->item_result
        columnList.add("g/L;g/dl&g/L;g/dl"); //result_info->item_unit
        columnList.add("高;高&低;低"); //result_info->item_hint
        return columnList;
    }

    public static List<String> getColumnTwo(){
        List<String> columnList = new ArrayList<>();
        columnList.add("save_emr");  //action
        columnList.add("auxiliary_exam_recommend;clinical_pathway_recommend");  //type
        columnList.add("23");  //param_visit_id
        columnList.add("123456789");  //param_patient_id
        columnList.add("呃呃呃呃"); //record_id
        columnList.add("达到顶峰"); //record_type
        columnList.add("呃呃呃呃"); //medical_id
        columnList.add("呃呃呃呃"); //visit_id
        columnList.add("呃呃呃呃"); //patient_info>id
        columnList.add("呃呃呃呃"); //patient_info>age
        columnList.add("呃呃呃呃"); //patient_info>gender
        columnList.add("呃呃呃呃"); //patient_info>birth_date
        columnList.add("呃呃呃呃"); //record_time
        columnList.add("呃呃呃呃"); //admission_time
        columnList.add("呃呃呃呃"); //hospital
        columnList.add("呃呃呃呃"); //dept
        columnList.add("呃呃呃呃"); //apply_dept
        columnList.add("呃呃呃呃"); //exam_name
        columnList.add("呃呃呃呃"); //exam_method
        columnList.add("呃呃呃呃"); //sample_category
        columnList.add("呃呃呃呃"); //sample_status
        columnList.add("呃呃呃呃"); //sample_id
        columnList.add("呃呃呃呃"); //sample_time
        columnList.add("呃呃呃呃"); //receive_time
        columnList.add("呃呃呃呃"); //exam_time
        columnList.add("呃呃呃呃"); //report_time
        columnList.add("呃呃呃呃;呃呃呃呃"); //result_info->item_name
        columnList.add("呃呃呃呃;呃呃呃呃"); //result_info->item_abbr
        columnList.add("呃呃呃呃;呃呃呃呃"); //result_info->item_result
        columnList.add("呃呃呃呃;呃呃呃呃"); //result_info->item_unit
        columnList.add("呃呃呃呃;呃呃呃呃"); //result_info->item_hint
        return columnList;
    }

    public static String getMapJson() {
        Map<String,Object> clinic = new HashMap<String,Object>();
        clinic.put("action","save_emr");
        List<Object> type= new ArrayList<Object>();
        clinic.put("type",type);
        List<Object> params= new ArrayList<Object>();
        clinic.put("params",params);
        List<Object> emrContents= new ArrayList<Object>();
        Map<String,Object> emrContentsMap = new HashMap<String,Object>();
        emrContentsMap.put("record_id","sd2313213123213");
        emrContentsMap.put("record_type","检验记录");
        emrContentsMap.put("medical_id","da324141");
        emrContentsMap.put("visit_id","ZY1232132131");
        Map<String,Object> patientInfo = new HashMap<String,Object>();
        patientInfo.put("id","2313123213");
        patientInfo.put("age","13岁");
        patientInfo.put("gender","1");
        patientInfo.put("birth_date","2008-11-1");
        emrContentsMap.put("patient_info",patientInfo);
        emrContentsMap.put("record_time","2021-10-23 11:11:11");
        emrContentsMap.put("admission_time","2021-10-23 08-23-25");
        emrContentsMap.put("hospital","广医附一");
        emrContentsMap.put("dept","心血管内科");
        emrContentsMap.put("apply_dept","心血管内科");
        emrContentsMap.put("exam_name","血常规");
        emrContentsMap.put("exam_method","血液标本");
        emrContentsMap.put("sample_category","血液");
        emrContentsMap.put("sample_status","合格");
        emrContentsMap.put("sample_id","23213");
        emrContentsMap.put("sample_time","2021-10-23 11:05:09");
        emrContentsMap.put("receive_time","2021-10-23 11:10:09");
        emrContentsMap.put("exam_time","2021-10-23 11:12:09");
        emrContentsMap.put("report_time","2021-10-23 11:15:09");
        List<Object> resultInfo= new ArrayList<Object>();
        Map<String,Object> resultInfoMapOne = new HashMap<String,Object>();
        resultInfoMapOne.put("item_name","血红蛋白");
        resultInfoMapOne.put("item_abbr","Hb");
        resultInfoMapOne.put("item_result","153");
        resultInfoMapOne.put("item_unit","g/L");
        resultInfoMapOne.put("item_hint","高");
        resultInfo.add(resultInfoMapOne);
        Map<String,Object> resultInfoMapTwo = new HashMap<String,Object>();
        resultInfoMapOne.put("item_name","白蛋白");
        resultInfoMapOne.put("item_abbr","albumin");
        resultInfoMapOne.put("item_result","70");
        resultInfoMapOne.put("item_unit","g/dl");
        resultInfoMapOne.put("item_hint","高");
        resultInfo.add(resultInfoMapOne);
        emrContentsMap.put("result_info",resultInfo);
        emrContents.add(emrContentsMap);
        clinic.put("emr_contents",emrContents);

        //System.out.println(clinic.toString());
        String clinicJson = JSON.toJSONString(clinic);
        System.out.println(clinicJson);
        return clinicJson;
    }
    public static List<String> getColumn(){
        List<String> columnList = new ArrayList<>();
        columnList.add("save_emr");  //action
        columnList.add("sd2313213123213"); //record_id
        columnList.add("检验记录"); //record_type
        columnList.add("da324141"); //medical_id
        columnList.add("ZY1232132131"); //visit_id
        columnList.add("2313123213"); //patient_info>id
        columnList.add("13岁"); //patient_info>age
        columnList.add("1"); //patient_info>gender
        columnList.add("2008-11-1"); //patient_info>birth_date
        columnList.add("2021-10-23 11:11:11"); //record_time
        columnList.add("2021-10-23 08-23-25"); //admission_time
        columnList.add("广医附一"); //hospital
        columnList.add("心血管内科"); //dept
        columnList.add("心血管内科"); //apply_dept
        columnList.add("血常规"); //exam_name
        columnList.add("血液标本"); //exam_method
        columnList.add("血液"); //sample_category
        columnList.add("合格"); //sample_status
        columnList.add("23213"); //sample_id
        columnList.add("2021-10-23 11:05:09"); //sample_time
        columnList.add("2021-10-23 11:10:09"); //receive_time
        columnList.add("2021-10-23 11:12:09"); //exam_time
        columnList.add("2021-10-23 11:15:09"); //report_time
        /*columnList.add("血红蛋白"); //result_info->item_name
        columnList.add("Hb"); //result_info->item_abbr
        columnList.add("153"); //result_info->item_result
        columnList.add("g/L"); //result_info->item_unit
        columnList.add("高"); //result_info->item_hint*/
        columnList.add("血红蛋白;白蛋白"); //result_info->item_name
        columnList.add("Hb;albumin"); //result_info->item_abbr
        columnList.add("153;70"); //result_info->item_result
        columnList.add("g/L;g/dl"); //result_info->item_unit
        columnList.add("高;高"); //result_info->item_hint
        return columnList;
    }

    public static String getJsonDefine(){
        //原子 1
        //对象 2
        //数组原子 3
        //数组对象4
        String jsonString = "{\n" +
                "\t\"fieldDelimiter\": \",\",\n" +
                "\t\"column\":[\n" +
                "\t\t{ \"element\":\"action\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"type\", \"type\":\"4\" },\n" +
                "\t\t{ \"element\":\"params\", \"type\":\"4\" },\n" +
                "\t\t{ \"element\":\"emr_contents\", \"type\":\"4\" },\n" +
                "\t\t{ \"element\":\"record_id\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"record_type\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"medical_id\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"visit_id\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"patient_info\", \"type\":\"42\" },\n" +
                "\t\t{ \"element\":\"id\", \"type\":\"421\" },\n" +
                "\t\t{ \"element\":\"age\", \"type\":\"421\" },\n" +
                "\t\t{ \"element\":\"gender\", \"type\":\"421\" },\n" +
                "\t\t{ \"element\":\"birth_date\", \"type\":\"421\" },\n" +
                "\t\t{ \"element\":\"record_time\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"admission_time\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"hospital\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"dept\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"apply_dept\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"exam_name\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"exam_method\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"sample_category\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"sample_status\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"sample_id\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"sample_time\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"receive_time\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"exam_time\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"report_time\", \"type\":\"41\" },\n" +
                "\t\t{ \"element\":\"result_info\", \"type\":\"44\" },\n" +
                "\t\t{ \"element\":\"item_name\", \"type\":\"441\" },\n" +
                "\t\t{ \"element\":\"item_abbr\", \"type\":\"441\" },\n" +
                "\t\t{ \"element\":\"item_result\", \"type\":\"441\" },\n" +
                "\t\t{ \"element\":\"item_unit\", \"type\":\"441\" },\n" +
                "\t\t{ \"element\":\"item_hint\", \"type\":\"441\" },\n" +
                "\t]\n" +
                "}";
        return jsonString;
    }
    public static String getJsonDefineTwo(){
        //原子 1
        //对象开始2
        //对象结束3
        //数组开始4
        //数组结束5
        String jsonString = "{\n" +
                "\t\"fieldDelimiter\": \",\",\n" +
                "\t\"column\":[\n" +
                "\t\t{ \"element\":\"action\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"type\", \"type\":\"4\" },\n" +
                "\t\t{ \"element\":\"type\", \"type\":\"5\" },\n" +
                "\t\t{ \"element\":\"params\", \"type\":\"4\" },\n" +
                "\t\t{ \"element\":\"params\", \"type\":\"5\" },\n" +
                "\t\t{ \"element\":\"emr_contents\", \"type\":\"4\" },\n" +
                "\t\t{ \"element\":\"emr_contents\", \"type\":\"2\" },\n" +
                "\t\t{ \"element\":\"record_id\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"record_type\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"medical_id\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"visit_id\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"patient_info\", \"type\":\"2\" },\n" +
                "\t\t{ \"element\":\"id\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"age\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"gender\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"birth_date\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"patient_info\", \"type\":\"3\" },\n" +
                "\t\t{ \"element\":\"record_time\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"admission_time\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"hospital\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"dept\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"apply_dept\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"exam_name\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"exam_method\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"sample_category\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"sample_status\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"sample_id\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"sample_time\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"receive_time\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"exam_time\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"report_time\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"result_info\", \"type\":\"4\" },\n" +
                "\t\t{ \"element\":\"result_info\", \"type\":\"2\" },\n" +
                "\t\t{ \"element\":\"item_name\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"item_abbr\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"item_result\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"item_unit\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"item_hint\", \"type\":\"1\" },\n" +
                "\t\t{ \"element\":\"result_info\", \"type\":\"3\" },\n" +
                "\t\t{ \"element\":\"result_info\", \"type\":\"5\" },\n" +
                "\t\t{ \"element\":\"emr_contents\", \"type\":\"3\" },\n" +
                "\t\t{ \"element\":\"emr_contents\", \"type\":\"5\" },\n" +
                "\t]\n" +
                "}";
        return jsonString;
    }
}
