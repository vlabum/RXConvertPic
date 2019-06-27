package ru.vlabum.android.gb.rxconvertpic.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import ru.vlabum.android.gb.rxconvertpic.R
import ru.vlabum.android.gb.rxconvertpic.model.IPicConverter
import ru.vlabum.android.gb.rxconvertpic.model.IPicture
import ru.vlabum.android.gb.rxconvertpic.model.PicConverter
import ru.vlabum.android.gb.rxconvertpic.model.Picture
import ru.vlabum.android.gb.rxconvertpic.presenter.MainPresenter
import ru.vlabum.android.gb.rxconvertpic.view.MainView

class MainActivity : MvpAppCompatActivity(), MainView {

    companion object {
        val PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123
        val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 124
        val GALLERY_REQUEST_CODE = 125
    }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        return MainPresenter(AndroidSchedulers.mainThread())
    }

    private var converter: IPicConverter? = null

    var dispose: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPermissions()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                if (data == null) return
                if (data.data == null) return
                val selectedImage: Uri = data.data!!
                val picture: IPicture = Picture(this.contentResolver, selectedImage)
                presenter.setConverter(PicConverter(picture))
                dispose = presenter.convertPicture()
                return
            }
        }
    }

    fun onClickCancel(view: View) {
        dispose?.dispose()
        rl_loading.visibility = View.GONE
        dispose = null
    }

    fun onClickOpenPic(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayListOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun getPermissions() {
        if (checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        }

        if (checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(WRITE_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        }
    }

    override fun showMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        rl_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        rl_loading.visibility = View.GONE
    }
}
