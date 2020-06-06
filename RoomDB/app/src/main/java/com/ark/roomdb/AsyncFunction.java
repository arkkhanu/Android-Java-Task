package com.ark.roomdb;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ark.roomdb.RoomDB.Bio;
import com.ark.roomdb.RoomDB.BioDao;

import java.lang.ref.WeakReference;


class InsertDaoAsync extends AsyncTask<Bio, Void, Boolean> {
    private BioDao dao;
    private WeakReference<Context> mContext;


    InsertDaoAsync(Context context, BioDao dao) {
        this.dao = dao;
        mContext = new WeakReference<>(context);
    }

    @Override
    protected Boolean doInBackground(Bio... bios) {
        dao.insertData(bios[0]);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            Toast.makeText(mContext.get(), "Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext.get(), "Problem Occur While Insertion", Toast.LENGTH_SHORT).show();
        }
    }
}

class GetBioAsync extends AsyncTask<Void, Void, Bio> {

    private BioDao dao;
    private WeakReference<Context> mContext;
    private int reg;
    private AsyncRespone delegate;

    GetBioAsync(Context context, BioDao dao, int reg, AsyncRespone delegate) {
        this.dao = dao;
        mContext = new WeakReference<>(context);
        this.reg = reg;
        this.delegate = delegate;
    }

    @Override
    protected Bio doInBackground(Void... voids) {
        return dao.getsearched(reg);
    }

    @Override
    protected void onPostExecute(Bio bio) {
        if (bio != null) {
            delegate.onFinishedReturn(bio);
            Toast.makeText(mContext.get(), "God" + bio.getRegNo(), Toast.LENGTH_SHORT).show();
        } else {
            delegate.onFinishedReturn(null);
        }
    }
}

class UpdateBioAsync extends AsyncTask<Bio, Void, Boolean> {

    private WeakReference<Context> mContext;
    private BioDao dao;

    UpdateBioAsync(Context context, BioDao dao) {
        mContext = new WeakReference<>(context);
        this.dao = dao;
    }

    @Override
    protected Boolean doInBackground(Bio... bios) {
        dao.updateBio(bios[0]);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            Toast.makeText(mContext.get(), "Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext.get(), "Problem Occur While Updation", Toast.LENGTH_SHORT).show();
        }
    }
}

class DeleteBioAsync extends AsyncTask<Void, Void, Boolean> {

    private WeakReference<Context> mContext;
    private BioDao dao;
    private int regNo;

    DeleteBioAsync(Context context, BioDao dao, int regNo) {
        mContext = new WeakReference<>(context);
        this.dao = dao;
        this.regNo = regNo;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        dao.deleteBio(regNo);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            Toast.makeText(mContext.get(), "Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext.get(), "Problem Occur While Deletion", Toast.LENGTH_SHORT).show();
        }
    }
}