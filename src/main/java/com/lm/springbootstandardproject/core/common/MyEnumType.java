package com.lm.springbootstandardproject.core.common;

import io.swagger.v3.oas.annotations.media.Schema;

public enum MyEnumType {


    @Schema(description = "资源库")
    c_resource_lib,


    @Schema(description = "学科分类")
    c_xueke,


    @Schema(description = "学科分类类型")
    c_xueke_type,


    @Schema(description = "专题分类")
    c_zhuanti,


    @Schema(description = "资源分类类型")
    c_zhuanti_type,


    @Schema(description = "友情链接")
    l_flink,


    @Schema(description = "军事风云")
    l_jsfy,


    @Schema(description = "军事期刊")
    l_jsqk,

    @Schema(description = "热词推荐")
    t_hot_word,

    @Schema(description = "指挥学报")
    l_zhxb,


    @Schema(description = "BANNER")
    l_banner,


    @Schema(description = "软件下载")
    l_software,


    @Schema(description = "资源库")
    l_resource_lib,


    @Schema(description = "情报资源")
    l_qbzy,


    @Schema(description = "电子图书")
    l_ebook,

    @Schema(description = "网络电视")
    l_tv,

    @Schema(description = "网络电视")
    l_movie,

    @Schema(description = "雪枫讲坛")
    l_xuefeng_talk,

    @Schema(description = "教学监控及录播")
    l_teach_video,


    @Schema(description = "纸质图书,新书推荐")
    l_paper_book_new,

    @Schema(description = "纸质图书排行")
    l_paper_book_ranking,

    @Schema(description = "纸质图书推荐")
    l_paper_book_recommend,


    @Schema(description = "所有链接点击类型")
    l_link,
    @Schema(description = "学科网站")
    l_xueke_web,

    @Schema(description = "导航栏")
    l_navigation,

    @Schema(description = "重要讲话")
    p_speech,


    @Schema(description = "政治理论")
    p_political,


    @Schema(description = "信息技术")
    l_techinfo,


    @Schema(description = "资源动态")
    p_resource_dynamic,

    @Schema(description = "所有文章类型")
    p_post,


    @Schema(description = "重要讲话, 原文")
    h_important_speech_text,


    @Schema(description = "banner图")
    h_banner,


    @Schema(description = "重要新闻, 原文")
    h_important_speech,


    @Schema(description = "重要新闻, 声音")
    h_important_speech_voice,


    @Schema(description = "重要新闻, 图集")
    h_important_speech_images,
    @Schema(description = "学科网站")
    h_xueke_web,

    @Schema(description = " 资源推荐")
    h_resource_recommend,


    @Schema(description = " 资源推荐热门")
    h_resource_recommend_hot,

    @Schema(description = " 资源推荐智能推荐")
    h_resource_recommend_intelligent,

    @Schema(description = " 资源动态")
    h_resource_dynamic,


    @Schema(description = " 资源库")
    h_resources_lib,


    @Schema(description = " 情报资源")
    h_Intelligence,


    @Schema(description = " 政治理论")
    h_political_theory,


    @Schema(description = "  学科资源")
    h_xueke_resources,


    @Schema(description = "电子图书")
    h_ebook,


    @Schema(description = "借阅排行")
    h_paper_book_ranking,

    @Schema(description = "新书上架")
    h_paper_book_new,


    @Schema(description = "好书推荐")
    h_paper_book_recommend,


    @Schema(description = "  专题资源")
    h_zhuanti_resources,


    @Schema(description = "  指挥学报")
    h_command_journal,


    @Schema(description = "  军事风云")
    h_military_situation,

    @Schema(description = "  军事期刊")
    h_military_journal,


    @Schema(description = "  信息技术")
    h_techinfo,


    @Schema(description = "  软件下载")
    h_software,

    @Schema(description = "  网络电视")
    h_tv,

    @Schema(description = "  陆指影院")
    h_movie,
    @Schema(description = "  纸质图书")
    l_paperbook,
    @Schema(description = "  雪枫讲坛")
    h_xuefeng_talk,

    @Schema(description = "  教学视频")
    h_teach_video,

    @Schema(description = "  热词推荐")
    h_hot_word,

    @Schema(description = "  经验学习")
    l_casestudy,

}
