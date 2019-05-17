package estyle.teabaike.fragment


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.ObservableSubscribeProxy
import estyle.base.BaseFragment
import estyle.base.rxjava.DisposableConverter
import estyle.base.rxjava.RefreshObserver
import estyle.teabaike.R
import estyle.teabaike.activity.ContentActivity
import estyle.teabaike.adapter.MainAdapter
import estyle.teabaike.config.Url
import estyle.teabaike.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelProviders.of(this)[MainViewModel::class.java] }

    // TODO 纳尼？
    val title by lazy { arguments!!.getString(TITLE) }
    private val type by lazy { arguments!!.getString(TYPE) }

    private val adapter by lazy { MainAdapter() }
    private var mRootView: View? = null
    private var mIsViewCreated = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (!mIsViewCreated) {
            mRootView = inflater.inflate(R.layout.fragment_main, container, false)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!mIsViewCreated) {
            adapter.emptyView = empty_view
            // todo context全部改为nonnull
            adapter.itemClickCallback = { ContentActivity.startActivity(context!!, it) }
            recycler_view.adapter = adapter

            viewModel.mainList.observe(this, Observer { adapter.submitList(it) })
            if (TextUtils.equals(type, Url.TYPES[0])) {
                viewModel.headerList.observe(this, Observer {
                    adapter.headlineList = it
                    adapter.onHeadlineViewHolderCreatedCallback = { holder ->
                        lifecycle.addObserver(holder)
                    }
                })
            }
            swipe_refresh_layout.setOnRefreshListener { refresh() }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!mIsViewCreated) {
            swipe_refresh_layout.post {
                swipe_refresh_layout.isRefreshing = true
                refresh()
            }

            mIsViewCreated = true
        }
    }

    // 数据回调
    private fun refresh() {
        viewModel.refresh(type)
            .`as`<ObservableSubscribeProxy<MainViewModel>>(DisposableConverter.dispose(this))
            .subscribe(object : RefreshObserver<MainViewModel>(swipe_refresh_layout) {
                override fun onError(e: Throwable) {
                    super.onError(e)
                    empty_view.setText(R.string.request_fail)
                }
            })
    }

    companion object {

        private const val TITLE = "title"
        private const val TYPE = "type"

        fun newInstance(title: String, type: String?): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                    putString(TYPE, type)
                }
            }
        }
    }
}