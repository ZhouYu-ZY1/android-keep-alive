//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package com.zhou.keepalive.entity

import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import android.os.RemoteException
import com.zhou.keepalive.entity.CactusConfig

interface ICactusInterface : IInterface {
    @Throws(RemoteException::class)
    fun wakeup(var1: CactusConfig?)

    @Throws(RemoteException::class)
    fun connectionTimes(var1: Int)

    class Default : ICactusInterface {
        @Throws(RemoteException::class)
        override fun wakeup(config: CactusConfig?) {
        }

        @Throws(RemoteException::class)
        override fun connectionTimes(time: Int) {
        }

        override fun asBinder(): IBinder? {
            return null
        }
    }

    open class Stub : Binder(), ICactusInterface {
        init {
            this.attachInterface(this, "com.gyf.cactus.entity.ICactusInterface")
        }

        override fun wakeup(var1: CactusConfig?) {

        }

        override fun connectionTimes(var1: Int) {

        }

        override fun asBinder(): IBinder {
            return this
        }

        @Throws(RemoteException::class)
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            val descriptor = "com.gyf.cactus.entity.ICactusInterface"
            when (code) {
                1 -> {
                    data.enforceInterface(descriptor)
                    val _arg0 = if (0 != data.readInt()) {
                        CactusConfig.CREATOR.createFromParcel(data)
                    } else {
                        null
                    }

                    this.wakeup(_arg0)
                    reply?.writeNoException()
                    return true
                }

                2 -> {
                    data.enforceInterface(descriptor)
                    val _arg1 = data.readInt()
                    this.connectionTimes(_arg1)
                    reply?.writeNoException()
                    return true
                }

                1598968902 -> {
                    reply?.writeString(descriptor)
                    return true
                }

                else -> return super.onTransact(code, data, reply, flags)
            }
        }

        private class Proxy(private val mRemote: IBinder) : ICactusInterface {
            override fun asBinder(): IBinder {
                return this.mRemote
            }

            val interfaceDescriptor: String
                get() = "com.gyf.cactus.entity.ICactusInterface"

            @Throws(RemoteException::class)
            override fun wakeup(config: CactusConfig?) {
                val _data = Parcel.obtain()
                val _reply = Parcel.obtain()

                try {
                    _data.writeInterfaceToken("com.gyf.cactus.entity.ICactusInterface")
                    if (config != null) {
                        _data.writeInt(1)
                        config.writeToParcel(_data, 0)
                    } else {
                        _data.writeInt(0)
                    }

                    val _status = mRemote.transact(1, _data, _reply, 0)
                    if (_status || defaultImpl == null) {
                        _reply.readException()
                        return
                    }

                    defaultImpl!!.wakeup(config)
                } finally {
                    _reply.recycle()
                    _data.recycle()
                }
            }

            @Throws(RemoteException::class)
            override fun connectionTimes(time: Int) {
                val _data = Parcel.obtain()
                val _reply = Parcel.obtain()

                try {
                    _data.writeInterfaceToken("com.gyf.cactus.entity.ICactusInterface")
                    _data.writeInt(time)
                    val _status = mRemote.transact(2, _data, _reply, 0)
                    if (_status || defaultImpl == null) {
                        _reply.readException()
                        return
                    }

                    defaultImpl!!.connectionTimes(time)
                } finally {
                    _reply.recycle()
                    _data.recycle()
                }
            }

            companion object {
                var sDefaultImpl: ICactusInterface? = null
            }
        }

        companion object {
            private const val DESCRIPTOR = "com.gyf.cactus.entity.ICactusInterface"
            const val TRANSACTION_wakeup: Int = 1
            const val TRANSACTION_connectionTimes: Int = 2

            fun asInterface(obj: IBinder?): ICactusInterface? {
                if (obj == null) {
                    return null
                } else {
                    val iin = obj.queryLocalInterface("com.gyf.cactus.entity.ICactusInterface")
                    return (if (iin != null && iin is ICactusInterface) iin else Proxy(
                        obj
                    ))
                }
            }

            fun setDefaultImpl(impl: ICactusInterface?): Boolean {
                if (Proxy.sDefaultImpl == null && impl != null) {
                    Proxy.sDefaultImpl = impl
                    return true
                } else {
                    return false
                }
            }

            val defaultImpl: ICactusInterface?
                get() = Proxy.sDefaultImpl
        }
    }
}
