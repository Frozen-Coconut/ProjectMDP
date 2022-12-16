package id.ac.istts.projectmdp.hospital

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import id.ac.istts.projectmdp.R
import id.ac.istts.projectmdp.databinding.FragmentPuskesmasProfileBinding

class PuskesmasProfileFragment : Fragment() {

    lateinit var tpNama:EditText
    lateinit var tpAlamat:EditText
    lateinit var tpNoHp:EditText
    lateinit var tpEmail:EditText
    lateinit var btnEdit:Button

    var edit = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_puskesmas_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tpNama = view.findViewById(R.id.profile_txtNama)
        tpAlamat = view.findViewById(R.id.profile_txtAlamat)
        tpNoHp = view.findViewById(R.id.profile_txtNoHP)
        tpEmail = view.findViewById(R.id.profile_txtEmail)

        btnEdit = view.findViewById(R.id.btnEdit)

        btnEdit.setOnClickListener {
            if (edit) {
                edit = false
                //Save data

                setOnEdit()
            }
            else {
                edit = true

                setOnEdit()
            }
        }
    }

    fun setOnEdit() {
        if (edit) {
            tpNama.isEnabled = true
            tpAlamat.isEnabled = true
            tpNoHp.isEnabled = true
            tpEmail.isEnabled = true
        }
        else {
            tpNama.isEnabled = false
            tpAlamat.isEnabled = false
            tpNoHp.isEnabled = false
            tpEmail.isEnabled = false
        }
    }
}