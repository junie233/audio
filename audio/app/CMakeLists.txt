
cmake_minimum_required(VERSION 3.4.1)

find_library(
              log-lib
              log )
set(pathToProject ../../../../libs)

# ----- fmode变声处理 ----- #
add_library(
            fmod
            SHARED
            IMPORTED)
set_target_properties(
            fmod
            PROPERTIES IMPORTED_LOCATION
            ../../../../libs/armeabi/libfmod.so
             )

add_library(
            fmodL
            SHARED
            IMPORTED)
set_target_properties(
            fmodL
            PROPERTIES IMPORTED_LOCATION
            ../../../../libs/armeabi/libfmodL.so
             )


# ----- fmode 变声处理JNI ----- #
add_library(
             voice_changer
             SHARED
             src/main/cpp/fmod/voice_changer.cpp)


# ----- 参数引入 ----- #
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

# -----  头文件引入 ----- #
include_directories(src/main/cpp/fmod/fmodinc)

# ----- 添加Library ----- #
target_link_libraries(
                        voice_changer
                        fmod
                        fmodL
                       ${log-lib} )