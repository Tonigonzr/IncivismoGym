package com.example.incivismogym;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.incivismogym.Incidencia;
import com.example.incivismogym.SharedViewModel;
import com.example.incivismogym.databinding.FragmentDashboardBinding;
import com.example.incivismogym.databinding.FragmentNotificationsBinding;
import com.example.incivismogym.databinding.NotificationsrowbindingBinding;
import com.example.incivismogym.ui.dashboard.DashboardViewModel;
import com.example.incivismogym.databinding.FragmentDashboardBinding;
import com.example.incivismogym.databinding.NotificationsrowbindingBinding;
import com.example.incivismogym.ui.dashboard.DashboardViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private FirebaseUser authUser;
    private String imagen = "https://firebasestorage.googleapis.com/v0/b/incivismogym.appspot.com/o/Fotos%2F";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        SharedViewModel sharedViewModel = new ViewModelProvider(
                requireActivity()
        ).get(SharedViewModel.class);


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        sharedViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            authUser = user;

            if (user != null) {
                DatabaseReference base = FirebaseDatabase.getInstance().getReference();

                DatabaseReference users = base.child("users");
                DatabaseReference uid = users.child(authUser.getUid());
                DatabaseReference incidencies = uid.child("incidencies");

                FirebaseRecyclerOptions<Incidencia> options = new FirebaseRecyclerOptions.Builder<Incidencia>()
                        .setQuery(incidencies, Incidencia.class)
                        .setLifecycleOwner(this)
                        .build();

                IncidenciaAdapter adapter = new IncidenciaAdapter(options);

                binding.rvIncidencies.setAdapter(adapter);
                binding.rvIncidencies.setLayoutManager(
                        new LinearLayoutManager(requireContext())
                );
                return;
            }
        });
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class IncidenciaAdapter extends FirebaseRecyclerAdapter<Incidencia, IncidenciaAdapter.IncidenciaViewholder> {
        public IncidenciaAdapter(@NonNull FirebaseRecyclerOptions<Incidencia> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(
                @NonNull IncidenciaViewholder holder, int position, @NonNull Incidencia model
        ) {
            try{
                Glide.with(getContext())
                        .load(imagen + model.getUrl() + "?alt=media&token=" + model.getUrl())
                        .into(holder.binding.imagenRow);
                holder.binding.txtDescripcio.setText(model.getProblema());
                holder.binding.txtAdreca.setText(model.getDireccio());
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        @NonNull
        @Override
        public IncidenciaViewholder onCreateViewHolder(
                @NonNull ViewGroup parent, int viewType
        ) {
            return new IncidenciaViewholder(NotificationsrowbindingBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent, false));
        }

        class IncidenciaViewholder extends RecyclerView.ViewHolder {
            NotificationsrowbindingBinding binding;

            public IncidenciaViewholder(NotificationsrowbindingBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}

