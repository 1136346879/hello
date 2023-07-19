package com.example.hello

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull
import com.hy.bco.camera.WelcomeActivity
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry


/** HelloPlugin */
class HelloPlugin:FlutterPlugin ,MethodCallHandler , ActivityAware
//  ,
//  PluginRegistry.ActivityResultListener
{

  private var mActivity: Activity? = null
  private val contactsCallBack: ContactsCallBack? = null
  private val CHANNEL = "hello"

  // 跳转原生选择联系人页面
  val METHOD_CALL_NATIVE = "selectContactNative"

  // 获取联系人列表
  val METHOD_CALL_LIST = "selectContactList"
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

//  override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?): Boolean {
//    Log.e("mActivity: ","activity 回调===========onActivityResult=====")
//    return false
//  }
  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, CHANNEL)
    channel.setMethodCallHandler(this)
    channel.invokeMethod("huichuan","123");
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")

      Log.e("mActivity: ","${mActivity.toString()}")
      mActivity?.startActivityForResult(Intent(mActivity, WelcomeActivity::class.java),9);
    } else {
      result.notImplemented()
    }
  }

  /** 获取通讯录回调.  */
  abstract class ContactsCallBack {
//    fun successWithList(contacts: List<HashMap?>?) {}
    fun successWithMap(map: HashMap<String?, String?>?) {}
    fun error() {}
  }


  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    mActivity = binding.activity
    binding.addActivityResultListener(
      PluginRegistry.ActivityResultListener {
          requestCode, resultCode, data ->
        if (requestCode == 9 && resultCode == 18) {
          Log.i("mActivity: ","activity 回调===========onActivityResult=====: "+ data?.getStringExtra("close"))
        }
        Log.i("mActivity: ","activity 回调===========onActivityResult=====")
        return@ActivityResultListener false;
    })
  }

  override fun onDetachedFromActivityForConfigChanges() {
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    mActivity = binding.activity
  }

  override fun onDetachedFromActivity() {
  }
}
