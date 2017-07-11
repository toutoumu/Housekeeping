package cn.jpush.api.push;

import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.common.HttpProxy;
import cn.jpush.api.common.IHttpClient;
import cn.jpush.api.common.NativeHttpClient;
import cn.jpush.api.common.ResponseWrapper;
import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.utils.StringUtils;

import com.google.common.base.Preconditions;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * 推送入口
 * 
 * For the following parameters, you can set them by instance creation. 
 * This action will override setting in PushPayload Optional.
 * * apnsProduction If not present, the default is true.
 * * timeToLive If not present, the default is 86400(s) (one day).
 * 
 * Can be used directly.
 */
public class PushClient {
	public static final String HOST_NAME_SSL = "https://api.jpush.cn";

	public static final String PUSH_PATH = "/v3/push";

	private final NativeHttpClient _httpClient;

	private JsonParser _jsonParser = new JsonParser();

	// If not present, true by default.
	private boolean _apnsProduction = true;

	// If not present, the default value is 86400(s) (one day)
	private long _timeToLive = 60 * 60 * 24;

	private boolean _globalSettingEnabled = false;

	private String _baseUrl;

	/**
	 * Create a Push Client.
	 * 
	 * @param masterSecret 应用标识(AppKey).
	 * @param appKey The KEY of one application on JPush.API MasterSecret
	 */
	public PushClient(String masterSecret, String appKey) {
		this(masterSecret, appKey, IHttpClient.DEFAULT_MAX_RETRY_TIMES);
	}

	public PushClient(String masterSecret, String appKey, int maxRetryTimes) {
		this(masterSecret, appKey, maxRetryTimes, null);
	}

	/**
	 * Create a Push Client with max retry times.
	 * 
	 * @param masterSecret  API access secret of the appKey.
	 * @param appKey The KEY of one application on JPush.
	 * @param maxRetryTimes max retry times
	 */
	public PushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
		ServiceHelper.checkBasic(appKey, masterSecret);

		String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
		this._baseUrl = HOST_NAME_SSL + PUSH_PATH;
		this._httpClient = new NativeHttpClient(authCode, maxRetryTimes, proxy);
	}

	/**
	 * Create a Push Client with global settings.
	 * 
	 * If you want different settings from default globally, this constructor is what you needed.
	 * 
	 * @param masterSecret API access secret of the appKey.
	 * @param appKey The KEY of one application on JPush.
	 * @param apnsProduction Global APNs environment setting. It will override PushPayload Options.
	 * @param timeToLive Global time_to_live setting. It will override PushPayload Options.
	 */
	public PushClient(String masterSecret, String appKey, boolean apnsProduction, long timeToLive) {
		this(masterSecret, appKey);

		this._apnsProduction = apnsProduction;
		this._timeToLive = timeToLive;
		this._globalSettingEnabled = true;
	}

	public void setDefaults(boolean apnsProduction, long timeToLive) {
		this._apnsProduction = apnsProduction;
		this._timeToLive = timeToLive;
		this._globalSettingEnabled = true;
	}

	public void setBaseUrl(String baseUrl) {
		this._baseUrl = baseUrl;
	}

	/**
	 * 推送消息
	 * @param pushPayload 可以由PushPayload.Builder构造器来构造
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public PushResult sendPush(PushPayload pushPayload) throws APIConnectionException, APIRequestException {
		Preconditions.checkArgument(!(null == pushPayload), "pushPayload should not be null");

		if (_globalSettingEnabled) {
			pushPayload.resetOptionsTimeToLive(_timeToLive);
			pushPayload.resetOptionsApnsProduction(_apnsProduction);
		}

		ResponseWrapper response = _httpClient.sendPost(_baseUrl, pushPayload.toString());

		return PushResult.fromResponse(response);
	}

	public PushResult sendPush(String payloadString) throws APIConnectionException, APIRequestException {
		Preconditions.checkArgument(StringUtils.isNotEmpty(payloadString), "pushPayload should not be empty");

		try {
			_jsonParser.parse(payloadString);
		} catch (JsonParseException e) {
			Preconditions.checkArgument(false, "payloadString should be a valid JSON string.");
		}

		ResponseWrapper response = _httpClient.sendPost(_baseUrl, payloadString);

		return PushResult.fromResponse(response);
	}

}
