package com.man.erpcenter.sales.client.constant;

public enum MqMsgInfoEnum {

	ADD_MSG_INFO(MqMsgInfoEnum.ERPCENTER_SALES_TOPIC, "add_msg_info_topic", "添加信息"),
	ADD_USER_INFO(MqMsgInfoEnum.ERPCENTER_USER_TOPIC, "add_user_info_tags", "添加用户信息"),
	ADD_USER_INFO_N(MqMsgInfoEnum.ERPCENTER_USER_TOPIC, "add_user_info_tags_n", "添加用户信息"),
	Q_CONFIG_COOKIE_PARAM(MqMsgInfoEnum.Q_COOKIE_PARAM_TOPIC,"config_cookie_param_tags","会话信息"),
	ADD_EMOT(MqMsgInfoEnum.Q_EMOT_TOPIC,"add_emot_tags","说信息"),
	ADD_EMOT_DB("ADD_EMOT_DB_TOPIC","ADD_EMOT_DB_tasg","说信息DB"),
	ADD_MSG("ADD_MSG_TOPIC","ADD_MSG_TAGS","msg信息"),
	ADD_MSG_DB("ADD_MSG_DB_TOPIC","ADD_MSG_DB_TAGS","msg信息DB"),
	ADD_PHOTO("ADD_PHOTO_TOPIC","ADD_PHOTO_TAGS","photo信息"),
	ADD_PHOTO_DB("ADD_PHOTO_DB_TOPIC","ADD_PHOTO_DB_TAGS","photo db信息"),
	ADD_VISIT_DB(MqMsgInfoEnum.Q_VISIT_TOPIC,"Q_VISIT_DB_TAGS","visitor info "),
	ADD_VISIT_DB_Q(MqMsgInfoEnum.Q_VISIT_TOPIC+"_Q","Q_VISIT_DB_TAGS_Q","visitor info  q库");
	private static final String ERPCENTER_SALES_TOPIC = "ERPCENTER_SALES_TOPIC";
	private  static final String ERPCENTER_USER_TOPIC = "ERPCENTER_USER_TOPIC";
	private static final String Q_COOKIE_PARAM_TOPIC = "Q_COOKIE_PARAM_TOPIC";
	private static final String Q_EMOT_TOPIC = "Q_EMOT_TOPIC";
	private static final String Q_VISIT_TOPIC = "Q_VISIT_TOPIC";
	/**
	 * 主题
	 */
	public String topic;

	/**
	 * 标签
	 */
	public String tags;

	/**
	 * 描述
	 */
	public String desc;

	
	private MqMsgInfoEnum(String topic, String tags, String desc) {
		this.topic = topic;
		this.tags = tags;
		this.desc = desc;
	}
	
	
}
