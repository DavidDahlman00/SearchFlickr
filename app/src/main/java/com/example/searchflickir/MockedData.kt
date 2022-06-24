package com.example.searchflickir

import com.example.searchflickir.network.ImageData

object MockedData {
    var data = mutableListOf<ImageData>()
    init {
        data.add(ImageData("52155941176","65535", "9e445e6a54","DSC_6549"))
        data.add(ImageData("52161964786", "debd25c339","65535", "cabo_priv_guide-58"))
        data.add(ImageData("52161964761", "93a9bccf79", "65535", "cabo_priv_guide-57" ))
        data.add(ImageData("52160943617", "6b9cc28345", "65535", "cabo_priv_guide-56" ))
        data.add(ImageData("52161964761", "93a9bccf79", "65535", "cabo_priv_guide-57" ))
        data.add(ImageData("52161964761", "93a9bccf79", "65535", "cabo_priv_guide-57" ))
        data.add(ImageData("52161964761", "93a9bccf79", "65535", "cabo_priv_guide-57" ))
        data.add(ImageData("52161964761", "93a9bccf79", "65535", "cabo_priv_guide-57" ))
        data.add(ImageData("52161964761", "93a9bccf79", "65535", "cabo_priv_guide-57" ))
    }
}