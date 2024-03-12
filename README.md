# NFT Avatar Plugin

NFT Avatar Plugin is a plugin for Arcturus Morningstar 3.5.x,
it allows you to add custom enables per avatar or clothing.

> [!WARNING]  
> This version is not maintained anymore,
> please use the new Kotlin version [here](https://github.com/brenoepics/NFTPlugin)

## How can I install it?

1. Download a pre-compiled version. [NFT-Avatar-Plugin](https://github.com/brenoepics/nftplugin/releases/)
2. Run the sql.
3. Paste the `Special-Looks.jar` file into your emulator's plugins folder and start/restart the emulator.
4. Now you need to give permission for your staffs to use update looks command, to do it open your database in the
   permissions table, and change the permission `cmd_update_looks`.
5. Then, open the `special_looks` table, and configure your looks.
6. Now, enter your hotel and type `:update_looks` and then try to wear a special outfit

### configuration

#### Permissions:

| Key              | Default Value |
|------------------|---------------|
| cmd_update_looks | 0             |

#### Emulator_texts:

| Key                                    | Default Value                    |
|----------------------------------------|----------------------------------|
| commands.description.cmd_update_looks  | :update_looks                    |
| commands.keys.cmd_update_looks         | update_looks                     |
| commands.cmd_update_looks.successfully | Successfully updated NFT Plugin! |

## How to add a new look?

open `special_looks` table

- `id` is auto incremented, you don't need to worry about it.
- `type` is the type of the look, it can be `figure` (full look string) or `setid` (the id of a clothing set)
- `figure` is the figure of the look, if the type is `figure` you need to put the look string here.
- `setid` is the id of the look, if the type is `setid` you need to put the clothing id here.
- `effect` is the effect that the look will give to the user.

### Example

Here is an example of:

1. A look that gives user effect 109, and it's a full-look string.
2. Clothing set 270 that gives user effect 108 which is the "lg-270" from the look string.

| id | type   | figure                                                                | setid | effect |
|----|--------|-----------------------------------------------------------------------|-------|--------|
| 1  | figure | lg-270-79.sh-305-62.ch-215-66.hr-100-0.ha-1002-70.hd-207-10.wa-2007-0 |       | 109    |
| 2  | setid  |                                                                       | 270   | 108    |

## Demo

![preview](https://imgur.com/qO5UGTi.gif)
